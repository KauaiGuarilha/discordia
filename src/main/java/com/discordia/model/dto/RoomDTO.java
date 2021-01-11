package com.discordia.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
