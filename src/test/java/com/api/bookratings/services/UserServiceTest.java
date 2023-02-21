package com.api.bookratings.services;

import com.api.bookratings.enums.Role;
import com.api.bookratings.exception.BadRequestException;
import com.api.bookratings.model.User;
import com.api.bookratings.repositories.UserRepository;
import com.api.bookratings.util.StartUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    void setup(){
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(StartUser.userExpected().getUsername());
        Mockito.when(userRepository.existsByUsername(StartUser.userDto().getUsername())).thenReturn(false);
        Mockito.when(userRepository.existsByUsername(StartUser.userDtoAlreadyExists().getUsername())).thenReturn(true);
        Mockito.when(passwordEncoder.encode(ArgumentMatchers.anyString())).thenReturn("$2a$12$RsGWddgEucF7nMKYrDRbme36la7N0cU/vmS6w0LSK48PRixmIOZnm");
        Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(StartUser.userExpected());
        Mockito.when(userRepository.findById(ArgumentMatchers.eq(1L))).thenReturn(StartUser.userOptional());
    }

    @Test
    void saveReturnsUserWhenSuccessful() {
        User savedUser = userService.save(StartUser.userDto());
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(savedUser.getUsername(), StartUser.userDto().getUsername());
        Assertions.assertEquals(savedUser.getPassword(),  "$2a$12$RsGWddgEucF7nMKYrDRbme36la7N0cU/vmS6w0LSK48PRixmIOZnm");
        Assertions.assertEquals(savedUser.getRole(), Role.USER);
    }

    @Test
    void saveThrowsBadRequestExceptionWhenUserAlreadyExists() {
        Assertions.assertThrows(BadRequestException.class, () -> userService.save(StartUser.userDtoAlreadyExists()));
    }

    @Test
    void updatePasswordReturnsPasswordWhenSuccessful(){
        Mockito.when(passwordEncoder.encode(ArgumentMatchers.anyString())).thenReturn(StartUser.passwordDto().getPassword());
        String updatedPassword = userService.updatePassword(StartUser.passwordDto());
        Assertions.assertEquals(updatedPassword, StartUser.passwordDto().getPassword());
    }

    @Test
    void existsByUsernameReturnsTrueWhenSuccessful(){
        Assertions.assertTrue(userService.existsByUsername(StartUser.userDtoAlreadyExists().getUsername()));
        Assertions.assertFalse(userService.existsByUsername("ronaldo"));
    }

    @Test
    void findByUsernameReturnsUserWhenSuccessful(){
        User findByUsernameUser = userService.findByUsername(StartUser.userExpected().getUsername());
        Assertions.assertEquals(findByUsernameUser.getId(), StartUser.userExpected().getId());
        Assertions.assertEquals(findByUsernameUser.getUsername(), StartUser.userExpected().getUsername());
        Assertions.assertEquals(findByUsernameUser.getPassword(), StartUser.userExpected().getPassword());
        Assertions.assertEquals(findByUsernameUser.getRole(), StartUser.userExpected().getRole());
    }

    @Test
    void findByIdReturnsAnUserOptionalWhenSuccessful(){
        Optional<User> userOptional = userService.findById(1L);
        User userFound = userOptional.orElse(null);
        assert userFound != null;
        Assertions.assertEquals(userFound.getId(), 1L);
        Assertions.assertEquals((userFound.getUsername()), StartUser.userExpected().getUsername());
        Assertions.assertEquals(userFound.getPassword(), StartUser.userExpected().getPassword());
        Assertions.assertEquals(userFound.getRole(), StartUser.userExpected().getRole());
    }

    @Test
    void deleteVerifyOneWhenSuccessful(){
        userService.delete(StartUser.userExpected());
        Mockito.verify(userRepository, Mockito.times(1)).delete(StartUser.userExpected());
    }

}