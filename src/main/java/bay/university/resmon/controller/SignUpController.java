package bay.university.resmon.controller;

import bay.university.resmon.dto.AuthenticationResponse;
import bay.university.resmon.dto.SignUpRequest;
import bay.university.resmon.dto.UserDto;
import bay.university.resmon.service.AuthenticationService;
import bay.university.resmon.service.impl.UserDetailsServiceImpl;
import bay.university.resmon.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SignUpController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public AuthenticationResponse signUp(@RequestBody SignUpRequest signUpRequest, HttpServletResponse response)
            throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        UserDto createdUser = authenticationService.createUser(signUpRequest);
        if(createdUser == null){
            return null;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(signUpRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        return new AuthenticationResponse(jwt);
    }
}
