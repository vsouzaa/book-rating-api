package com.api.bookrating.util;

import com.api.bookrating.dto.PasswordDto;
import com.api.bookrating.dto.UserDto;
import com.api.bookrating.enums.Role;
import com.api.bookrating.model.User;
import java.util.Optional;
public class StartUser {

    public static User userExpected(){
        return new User(1L, "mbappe", "$2a$12$RsGWddgEucF7nMKYrDRbme36la7N0cU/vmS6w0LSK48PRixmIOZnm", Role.USER);
    }
    public static Optional<User> userOptional(){
        return Optional.of(new User(1L, "mbappe", "$2a$12$RsGWddgEucF7nMKYrDRbme36la7N0cU/vmS6w0LSK48PRixmIOZnm", Role.USER));
    }
    public static UserDto userDtoAlreadyExists(){
        return new UserDto("messi","$2a$12$RsGWddgEucF7nMKYrDRbme36la7N0cU/vmS6w0LSK48PRixmIOZnm");
    }
    public static UserDto userDto(){
        return new UserDto("mbappe","$2a$12$RsGWddgEucF7nMKYrDRbme36la7N0cU/vmS6w0LSK48PRixmIOZnm");
    }

    public static PasswordDto passwordDto(){
        return new PasswordDto("$2a$12$L70t4Ag0d4WfHBI8e1wd5.FHkXk9rn5CZttsoFv.M6h8BmyaWviZq");
    }
}
