package com.api.bookratings.controllers;

import com.api.bookratings.dto.PasswordDto;
import com.api.bookratings.dto.UserDto;
import com.api.bookratings.services.UserService;
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
