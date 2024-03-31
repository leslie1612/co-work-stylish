package tw.appworks.school.example.stylish.repository.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.appworks.school.example.stylish.model.user.User;
import tw.appworks.school.example.stylish.repository.user.UserRepository;

import java.util.Map;

@Repository
public interface CouponRepository extends JpaRepository<User, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = """
            update user set coupon = coupon + :num where id =:userId
            """,
            nativeQuery = true)
    int updateCoupontoUser(@Param("num") Integer num,
                            @Param("userId") Integer userId);


    @Query(value = "select u.coupon from User u where u.id = :userId")
    Integer getTotalCouponByUser(@Param("userId") Integer userId);

    @Query(value = """
            SELECT coupon FROM user WHERE id = :userId
            """,
            nativeQuery = true)
    Map<String, Object> getCouponByUser(@Param("userId") Integer userID);
}
