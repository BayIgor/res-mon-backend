package bay.university.resmon.service.impl;

import bay.university.resmon.dto.SignUpRequest;
import bay.university.resmon.dto.UserDto;
import bay.university.resmon.model.User;
import bay.university.resmon.repository.UserRepository;
import bay.university.resmon.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(SignUpRequest signUpRequest){
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        User createdUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        userDto.setEmail(createdUser.getEmail());
        userDto.setName(createdUser.getName());
        userDto.setPassword(createdUser.getPassword());
        return userDto;
    }
}
