package bay.university.resmon.service;

import bay.university.resmon.dto.UserDto;

public interface PasswordResetService {
    UserDto updateUserPassword(String username, String password);
}
