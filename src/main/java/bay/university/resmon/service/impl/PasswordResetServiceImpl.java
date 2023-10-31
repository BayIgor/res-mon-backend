package bay.university.resmon.service.impl;

import bay.university.resmon.dto.UserDto;
import bay.university.resmon.model.User;
import bay.university.resmon.repository.UserRepository;
import bay.university.resmon.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto updateUserPassword(String username, String password){
        User user = userRepository.findFirstByEmail(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        User updatedUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(updatedUser.getId());
        userDto.setEmail(updatedUser.getEmail());
        userDto.setName(updatedUser.getName());
        userDto.setPassword(updatedUser.getPassword());
        return userDto;
    }
}
