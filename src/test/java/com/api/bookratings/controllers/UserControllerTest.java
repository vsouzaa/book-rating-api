package com.api.bookratings.controllers;

import com.api.bookratings.services.UserService;
import com.api.bookratings.util.StartUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @BeforeEach
    void setup(){
        Mockito.when(userService.save(ArgumentMatchers.any())).thenReturn(StartUser.userExpected());
    }

    @Test
    void saveReturnsUsernameWhenSuccessful() {
        String savedUser = userController.save(StartUser.userDto());
        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(savedUser.contains(StartUser.userDto().getUsername()));

    }

    @Test
    void updatePasswordReturnsPasswordWhenSuccessful(){
        String updatedUserPassword = userController.updatePassword(StartUser.passwordDto());
        Assertions.assertTrue(updatedUserPassword.contains(StartUser.passwordDto().getPassword()));
    }
}