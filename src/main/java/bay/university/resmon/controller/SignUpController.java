package bay.university.resmon.controller;

import bay.university.resmon.dto.auth.SignUpRequest;
import bay.university.resmon.dto.UserDto;
import bay.university.resmon.service.AuthenticationService;
import bay.university.resmon.service.impl.UserDetailsServiceImpl;
import bay.university.resmon.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody SignUpRequest signUpRequest)
            throws BadCredentialsException, DisabledException, UsernameNotFoundException {
        if (userDetailsService.loadUserByUsername(signUpRequest.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Такая почта уже существует, попробуйте войти");
        }

        UserDto createdUser = authenticationService.createUser(signUpRequest);
        if (createdUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка при создании пользователя");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(signUpRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK)
                .body(jwt);
    }
}
