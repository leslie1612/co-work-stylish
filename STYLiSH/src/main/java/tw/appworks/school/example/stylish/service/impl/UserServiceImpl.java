package tw.appworks.school.example.stylish.service.impl;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;
import tw.appworks.school.example.stylish.data.dto.FacebookProfile;
import tw.appworks.school.example.stylish.data.dto.SignInDto;
import tw.appworks.school.example.stylish.data.dto.UserDto;
import tw.appworks.school.example.stylish.data.form.SignupForm;
import tw.appworks.school.example.stylish.middleware.JwtTokenUtil;
import tw.appworks.school.example.stylish.model.user.Role;
import tw.appworks.school.example.stylish.model.user.User;
import tw.appworks.school.example.stylish.repository.user.RoleRepository;
import tw.appworks.school.example.stylish.repository.user.UserRepository;
import tw.appworks.school.example.stylish.service.UserService;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    private final MailGunServiceImpl mailGunService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, MailGunServiceImpl mailGunService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.mailGunService = mailGunService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username); // we use email as username
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with username : " + username);
        }
        return userRepository.getUserDetails(user.getEmail(), user.getPassword(), user.getRole().getName());
    }

    @Override
    public UserDto getUserDtoByToken(String token) throws UserNotExistException {
        return UserDto.from(getUserByToken(token));
    }

    @Override
    public User getUserByToken(String token) throws UserNotExistException {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw new UserNotExistException("User Not Found with username : " + username);
        }
        return user;
    }

    @Override
    public SignInDto signup(SignupForm signupForm, String role) throws UserExistException, RoleNotFoundException, MessagingException, UnsupportedEncodingException {
        User user = userRepository.findUserByEmail(signupForm.getEmail());
        if (user != null) {
            throw new UserExistException(signupForm.getEmail() + " is already exist");
        }


        // 需要自己加入role嗎？
        Role r = roleRepository.findRoleByName(role);
        if (r == null) {
            logger.warn("role: " + role + " not found");
            throw new RoleNotFoundException(role + " not found");
        }

        User saved = userRepository.save(createUser(signupForm, NATIVE, r));
        //mailgun發送認證信
        String result = mailGunService.sendRegisterMail(saved);
        log.info(result);
        return SignInDto.from(saved);
    }

    @Override
    public SignInDto nativeSign(String email, String password)
            throws UserNotExistException, UserPasswordMismatchException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UserNotExistException("User Not Found with email : " + email);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserPasswordMismatchException("Wrong Password");
        }


        String token = jwtTokenUtil.generateToken(email, List.of(user.getRole().getName()));
        user.setAccessToken(token);
        user.setAccessExpired(jwtTokenUtil.getExpirationDateFromToken(token).getTime());
        user.setLoginAt(Timestamp.from(Instant.now()));

        return SignInDto.from(user);
    }

    @Override
    public SignInDto facebookSign(String token, String role) throws UserNotExistException, RoleNotFoundException {
        final String uri = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + token;
        return WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(FacebookProfile.class)
                .publishOn(Schedulers.boundedElastic())
                .map(profile -> {
                    Role r = roleRepository.findRoleByName(role);

                    //===========================
                    return SignInDto.from(createUser(profile.getName(), profile.getEmail(), null, FACEBOOK,
                            "https://graph.facebook.com/" + profile.getId() + "/picture?type=large", r,null, null,null,0));
                })
                .block();
    }

    // get user info from signup form
    @Nonnull
    private User createUser(SignupForm signupForm, String provider, Role role) {
        return createUser(signupForm.getName(), signupForm.getEmail(),
                signupForm.getPassword(), provider, null,
                role,signupForm.getFavorColor(),signupForm.getBirthday(), signupForm.getGender(),0);
    }

    // create user by native and fb
    @Nonnull
    private User createUser(String name, String email, @Nullable String password, String provider,
                            @Nullable String picture, Role role, String color,String birthday,String gender,Integer coupon) {
        String token = jwtTokenUtil.generateToken(email, List.of(role.getName()));
        User user = new User();
        user.setRole(role);
        user.setProvider(provider);
        user.setEmail(email);
        if (password != null)
            user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        if (picture != null)
            user.setPicture(picture);
        user.setAccessToken(token);
        user.setAccessExpired(jwtTokenUtil.getExpirationDateFromToken(token).getTime());
        user.setLoginAt(Timestamp.from(Instant.now()));

        //===========================
        user.setFavorColor(color);
        user.setBirthday(birthday);
        user.setGender(gender);
        user.setCoupon(coupon);
        return user;
    }

}
