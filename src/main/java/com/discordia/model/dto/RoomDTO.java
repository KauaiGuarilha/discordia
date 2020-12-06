package com.discordia.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    private String roomName;
    private List<UserDTO> users;
    private UserDTO userHost;
    private Integer capacityLimit;
}
