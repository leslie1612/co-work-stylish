//package tw.appworks.school.example.stylish.model.coupon;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import tw.appworks.school.example.stylish.model.user.User;
//
//@Entity
//@Table(name = "coupon")
//@Data
//@NoArgsConstructor
//public class Coupon {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private  Integer id;
//
//    @ManyToOne
//    @JoinColumn()
//    private User user;
//
//    @Column(name="count")
//    private Integer count;
//
//
//}
