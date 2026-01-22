package ru.artteam.spendly_app.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artteam.spendly_app.domain.UserEntity;
import ru.artteam.spendly_app.dto.req.UserRegistrationDto;
import ru.artteam.spendly_app.dto.res.UserResponseDto;
import ru.artteam.spendly_app.repository.UserRepository;
import ru.artteam.spendly_app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    @GetMapping
    @JsonIgnore
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> createUser(
            @RequestBody @Valid UserRegistrationDto dto
    ){
        UserEntity createdUser = userService.createUser(dto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
