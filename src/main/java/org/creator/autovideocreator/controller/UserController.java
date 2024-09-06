package org.creator.autovideocreator.controller;

import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.dto.impl.CreateUserDto;
import org.creator.autovideocreator.dto.impl.UserDto;
import org.creator.autovideocreator.service.database.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping()
    public UserDto createUser(@RequestBody CreateUserDto createUserDto) {
        return userService.save(createUserDto);
    }
}
