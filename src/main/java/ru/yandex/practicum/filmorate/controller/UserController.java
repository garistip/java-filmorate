package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public User create(@RequestBody User user) throws ValidationException {
        return userService.create(user);
    }

    @PutMapping("/users")
    public User put(@RequestBody User user) throws ValidationException {
        return userService.put(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Integer id,
                          @PathVariable("friendId") Integer friendId) throws ValidationException {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void delFriend(@PathVariable("id") Integer id,
                          @PathVariable("friendId") Integer friendId) throws ValidationException {
        userService.delFriend(id, friendId);
    }

    @GetMapping("/users")
    public Collection<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> getFriends(@PathVariable("id") Integer id) {
        return userService.getFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable("id") Integer id,
                                             @PathVariable("otherId") Integer otherId) {
        return userService.getCommonFriends(id, otherId);
    }

}
