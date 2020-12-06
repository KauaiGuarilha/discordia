package com.discordia.model.entity;

import com.discordia.model.dto.RoomDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RoomManager {

    private static List<RoomDTO> roomList = new ArrayList<>();

    public static List<RoomDTO> getRoomList() {
        return roomList;
    }

    public static void setRoomList(RoomDTO room) {
        room.setId(UUID.randomUUID());
        RoomManager.roomList.add(room);
    }

    public static void removeRoom(RoomDTO room){
        RoomManager.roomList.remove(room);
    }
}
