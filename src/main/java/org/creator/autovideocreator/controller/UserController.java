package org.creator.autovideocreator.controller;

import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.dto.user.CreateUserDto;
import org.creator.autovideocreator.dto.user.ResponseUserDto;
import org.creator.autovideocreator.service.data.UserService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping()
    public ResponseUserDto createUser(@RequestBody CreateUserDto createUserDto) {
        return userService.save(createUserDto);
    }

    @GetMapping("/{telegramId}")
    public ResponseUserDto getUser(@PathVariable String telegramId) {
        return userService.getByTelegramId(telegramId);
    }
}
