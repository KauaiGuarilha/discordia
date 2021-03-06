package com.discordia.model.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistryDTO extends UserDTO {

    @NotBlank(message = "Please enter the password")
    private String password;

    @NotBlank(message = "Please enter the mobileToken")
    private String mobileToken;
}
