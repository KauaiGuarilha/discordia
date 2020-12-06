package com.discordia.model.factory;

import com.discordia.model.dto.RoomDTOResponse;
import com.discordia.model.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomDTOResponseFactory {

    public RoomDTOResponse dtoToResponse(Room dto){
        return RoomDTOResponse.builder()
                .id(dto.getId())
                .roomName(dto.getRoomName())
                .userHost(dto.getUserHost().getName())
                .capacityLimit(dto.getCapacityLimit())
                .build();
    }
}
