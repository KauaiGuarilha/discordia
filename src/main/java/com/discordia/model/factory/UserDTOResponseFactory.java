package com.discordia.model.factory;

import com.discordia.model.dto.UserDTO;
import com.discordia.model.dto.UserRegistryDTOResponse;
import com.discordia.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOResponseFactory {

    public UserRegistryDTOResponse userToResponse(User user) {
        return UserRegistryDTOResponse.builder()
                .name(user.getName())
                .password(user.getPassword())
                .mobileToken(user.getMobileToken())
                .build();
    }

    public User toUser(UserDTO dto) {
        return User.builder()
                .name(dto.getName())
                .password(dto.getPassword())
                .mobileToken(dto.getMobileToken())
                .build();
    }
}
