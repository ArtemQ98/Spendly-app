package ru.artteam.spendly_app.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.artteam.spendly_app.domain.UserEntity;
import ru.artteam.spendly_app.dto.req.UserRegistrationDto;
import ru.artteam.spendly_app.dto.res.UserResponseDto;
import ru.artteam.spendly_app.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    final private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserEntity createUser(UserRegistrationDto dto){
        if (userRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("A user with such an email already exists");
        }

        UserEntity user = new UserEntity();

        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        return userRepository.save(user);
    }

    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAll().stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    private UserResponseDto mapToResponseDto(UserEntity user){
       return UserResponseDto.builder()
               .id(user.getId())
               .username(user.getUsername())
               .email(user.getEmail())
               .build();

    }

}
