package com.discordia.model.factory;

import com.discordia.model.dto.*;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserRoomsDTOResponseFactory {

    public UserRoomsDTOResponse dtoToUserRooms(String user, List<RoomDTOResponse> rooms) {
        return UserRoomsDTOResponse.builder().username(user).rooms(rooms).build();
    }
}
