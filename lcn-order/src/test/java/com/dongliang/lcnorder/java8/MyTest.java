package com.dongliang.lcnorder.java8;

import com.dongliang.lcnorder.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author D-L
 * @version 1.0.0
 * @ClassName MyTest.java
 * @Description TODO
 * @createTime 2021-05-19 10:58:00
 */
@SpringBootTest
public class MyTest {

    @Test
    public void testTest(){
//        Consumer<String> consumer = (x)-> System.out.println(x);
//        consumer.accept("Hello!");



        User user = new User();
        user.setNickName("aaa");
        Supplier<String> supplier = user::getNickName;
        String str = supplier.get();
        System.out.println(str);
    }


    public static void main(String[] args) {
//        User user = new User();
//        user.setNickName("aaa");
//        Supplier<String> supplier = user::getNickName;
//        String str = supplier.get();
//        System.out.println(str);


//        Supplier<User> supplier = ()->new User();
//        Supplier<User> supplier = User::new;
//        User user = supplier.get();
//        System.out.println(user);



//        Function<String, User> function = User::new;
//        User user = function.apply("zhangsan");
//        System.out.println(user);

        List<User> userList = Arrays.asList(
                new User("张三", 20, "123456"),
                new User("李四", 30, "123456"),
                new User("王五", 25, "123456"),
                new User("赵六", 42, "123456")
        );
//        List<String> collect = userList.stream().map(User::getNickName).collect(Collectors.toList());
////                .forEach(System.out::println);
//        System.out.println(collect.get(0));




//        List<Integer> list = Arrays.asList(3, 1, 6, 7, 5, 9);
//        list.stream()
//                .sorted()
//                .forEach(System.out::println);



        userList.stream()
                .sorted((u1, u2) -> {
                    if (u1.getAge().equals(u2.getAge())) {
                        return u1.getNickName().compareTo(u2.getNickName());
                    } else {
                        return u1.getAge().compareTo(u2.getAge());
                    }
                })
                .forEach(System.out::println);
    }
}
