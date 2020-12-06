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
public class UserRoomsDTOResponse {

    private String username;
    private List<RoomDTOResponse> rooms;
}
