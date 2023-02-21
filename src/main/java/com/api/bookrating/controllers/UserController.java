package com.api.bookrating.controllers;

import com.api.bookrating.dto.PasswordDto;
import com.api.bookrating.dto.UserDto;
import com.api.bookrating.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping(path = "/register")
    public String save(@RequestBody @Valid UserDto userDto){
        userService.save(userDto);
        return "User: " + userDto.getUsername() + " created!";
    }
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PutMapping(path = "/update-password")
    public String updatePassword(@RequestBody @Valid PasswordDto passwordDto){
        userService.updatePassword(passwordDto);
        return passwordDto.getPassword() + " password updated";
    }
}
