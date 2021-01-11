package com.discordia.model.service;

import com.discordia.model.dto.ChangeUserDTO;
import com.discordia.model.dto.RoomDTO;
import com.discordia.model.dto.UserDTO;
import com.discordia.model.entity.Room;
import com.discordia.model.entity.User;
import com.discordia.model.repository.RoomRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired UserService userService;
    @Autowired RoomRepository roomRepository;

    public Room createRoom(RoomDTO dto) {
        List<User> users = new ArrayList<>();
        User userHost = userService.returnUser(dto.getUserHost().getName());

        for (UserDTO userDTO : dto.getUsers()) {
            User userBase = userService.returnUser(userDTO.getName());

            if (userBase != null) users.add(userBase);
        }

        Room room =
                Room.builder()
                        .id(UUID.randomUUID())
                        .roomName(dto.getRoomName())
                        .users(users)
                        .userHost(userHost)
                        .capacityLimit(dto.getCapacityLimit())
                        .build();

        roomRepository.save(room);
        return room;
    }

    public Room returnInfoRoom(UUID idRoom) {
        Optional<Room> roomBase = roomRepository.findById(idRoom);

        if (roomBase.isPresent()) return roomBase.get();
        throw new RuntimeException("Could not get room information.");
    }

    public void enterRoom(UserDTO dto, UUID idRoom) {

        Optional<Room> roomBase = roomRepository.findById(idRoom);

        if (roomBase.isPresent()) {
            Room room = roomBase.get();

            if (room.getUsers().size() <= room.getCapacityLimit()) {
                User userBase = userService.returnUser(dto.getName());
                room.getUsers().add(userBase);

                roomRepository.save(room);
                return;
            }
            throw new RuntimeException("Could not enter the room.");
        } else throw new RuntimeException("Could not find the room.");
    }

    public void alterRoomHost(ChangeUserDTO dto, UUID idRoom) {

        Optional<Room> roomBase = roomRepository.findById(idRoom);

        if (roomBase.isPresent()) {
            Room room = roomBase.get();

            if (room.getUserHost().equals(dto.getUserHost())) {
                User userBase = userService.returnUser(dto.getUserChange().getName());

                room.setUserHost(userBase);
                return;
            }
            throw new RuntimeException("Could not change room host.");
        }
    }

    public void leaveRoom(UserDTO dto, UUID idRoom) {

        Optional<Room> roomBase = roomRepository.findById(idRoom);

        if (roomBase.isPresent()) {
            Room room = roomBase.get();

            // TODO Testar se o contains está verificando o objeto inteiro
            if (room.getUsers().contains(dto)) {
                room.getUsers().remove(dto);
                return;
            }

            throw new RuntimeException("Could not leave the room.");
        }
    }

    public List<Room> returnRoomsByUser(String username) {

        Optional<List<Room>> roomsBase = roomRepository.findByUserName(username);

        if (roomsBase.isPresent()) {
            return roomsBase.get();
        }

        throw new RuntimeException("Could not leave the room.");
    }
}
