package com.discordia.model.service;

import com.discordia.model.dto.ChangeUserDTO;
import com.discordia.model.dto.RoomDTO;
import com.discordia.model.dto.UserDTO;
import com.discordia.model.entity.Room;
import com.discordia.model.entity.User;
import com.discordia.model.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService {

    @Autowired UserService userService;
    @Autowired RoomRepository roomRepository;

    public Room createRoom(RoomDTO dto){
        List<User> users = new ArrayList<>();
        User userHost = userService.returnUser(dto.getUserHost().getName());

        for (UserDTO userDTO : dto.getUsers()){
            User userBase = userService.returnUser(userDTO.getName());

            if (userBase != null) users.add(userBase);
        }

        Room room = Room.builder()
                .id(UUID.randomUUID())
                .roomName(dto.getRoomName())
                .users(users)
                .userHost(userHost)
                .capacityLimit(dto.getCapacityLimit())
                .build();

        roomRepository.save(room);
        return room;
    }

    public Room returnInfoRoom(UUID idRoom){
        Optional<Room> roomBase = roomRepository.findById(idRoom);

        if (roomBase.isPresent()) return roomBase.get();
        throw new RuntimeException("Could not get room information.");
    }

    public void enterRoom(UserDTO dto, UUID idRoom){

        Optional<Room> roomBase = roomRepository.findById(idRoom);

        if (roomBase.isPresent()) {
            Room room = roomBase.get();

            if (room.getUsers().size() <= room.getCapacityLimit()) {
                User userBase = userService.returnUser(dto.getName());
                room.getUsers().add(userBase);

                roomRepository.save(room);
            }
            else
                throw new RuntimeException("Could not enter the room.");

        }
        else
            throw new RuntimeException("Could not find the room.");
    }

    public void alterRoomHost(ChangeUserDTO dto, UUID idRoom){

        Optional<Room> roomBase = roomRepository.findById(idRoom);

        if (roomBase.isPresent()) {
            Room room = roomBase.get();

            if (room.getUserHost().equals(dto.getUserHost()))
                User userBase = userService.returnUser();

                room.setUserHost(userBase);
            else
                throw new RuntimeException("Could not change room host.");
        }


        for (RoomDTO room : roomRepository.getRoomList()){
            if(room.getId().equals(idRoom)) {
                if (room.getUserHost().equals(dto.getUserHost()))
                    room.setUserHost(dto.getUserChange());
                else
                    throw new RuntimeException("Could not change room host.");
            }
        }
    }

    public void leaveRoom(UserDTO dto, UUID idRoom){

        for (RoomDTO room : roomRepository.getRoomList()){
            if(room.getId().equals(idRoom)) {
                if (room.getUsers().contains(dto))
                    room.getUsers().remove(dto);
                else
                    throw new RuntimeException("Could not leave the room.");
            }
        }
    }

    public List<RoomDTO> returnRoomsByUser(String username){
        return findRooms(username);
    }

    private List<RoomDTO> findRooms(String username){
        List<RoomDTO> rooms = new ArrayList<>();
        for (RoomDTO room : roomRepository.getRoomList()) {
            boolean userFinded = false;
            for (UserDTO user : room.getUsers()) {
                if (user.getName().equals(username)) {
                    rooms.add(room);
                    userFinded = true;
                    break;
                }
            }
            if (userFinded){
                continue;
            }
        }
        return rooms;
    }
}