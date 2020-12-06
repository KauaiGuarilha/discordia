package com.discordia.model.factory;

import com.discordia.model.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRoomsDTOResponseFactory {

    public UserRoomsDTOResponse dtoToUserRooms(String user, List<RoomDTOResponse> rooms){
        return UserRoomsDTOResponse.builder()
                .username(user)
                .rooms(rooms)
                .build();
    }
}
