package bay.university.resmon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResourceMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceMonitorApplication.class, args);
    }

//    public void run(String... args) {
//        User adminAccount = userRepository.findByRole(Role.ADMIN);
//        if (null == adminAccount) {
//            User user = new User();
//
//            user.setEmail("baydev2002@gmail.com");
//            user.setFirstName("admin");
//            user.setLastName("admin");
//            user.setRole(Role.ADMIN);
//            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
//            userRepository.save(user);
//        }
//    }
}
