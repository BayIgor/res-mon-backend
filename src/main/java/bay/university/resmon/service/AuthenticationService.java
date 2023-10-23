package bay.university.resmon.service;

import bay.university.resmon.dto.SignUpRequest;
import bay.university.resmon.dto.UserDto;

public interface AuthenticationService {
    UserDto createUser(SignUpRequest signUpRequest);
}
