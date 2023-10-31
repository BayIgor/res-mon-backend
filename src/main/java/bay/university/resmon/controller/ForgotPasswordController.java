package bay.university.resmon.controller;

import bay.university.resmon.dto.PasswordForgotRequest;
import bay.university.resmon.dto.PasswordResetRequest;
import bay.university.resmon.service.EmailService;
import bay.university.resmon.service.PasswordResetService;
import bay.university.resmon.service.impl.UserDetailsServiceImpl;
import bay.university.resmon.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgotPasswordController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Environment environment;

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot_password")
    public String forgotPassword(@RequestBody PasswordForgotRequest passwordResetRequest) {
        try {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(passwordResetRequest.getEmail());

            final String jwt = jwtUtil.generateToken(userDetails.getUsername());

            String resetUrl = environment.getProperty("app.client.url") + "reset_password?jwt=" + jwt;

            String emailMessage = String.format("Для сброса пароля перейдите по ссылке %s\n" +
                    "Если вы не забывали пароль просто удалите это сообщение\n\n" +
                    "Для помощи вы можете обратиться в команду поддержки:\n%s", resetUrl, environment.getProperty("app.admin.email"));

            emailService.sendEmail(passwordResetRequest.getEmail(), "Сброс пароля", emailMessage);
            return "Сообщение на почту успешно отправлено";
        }
        catch (UsernameNotFoundException ex){
            return "Такой почты не существует в нашей базе";
        }
    }

    @PostMapping("/reset_password")
    public String resetPassword(@RequestBody PasswordResetRequest passwordResetRequest){

        String jwtToken = passwordResetRequest.getJwt();
        String secretKey = environment.getProperty("app.jwt.secret");

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken)
                    .getBody();

            String username = claims.get("sub", String.class);

            passwordResetService.updateUserPassword(username, passwordResetRequest.getPassword());

            return "Пароль успешно изменён";
        } catch (Exception e) {
           return "Не верная ссылка, получите новую";
        }
    }
}
