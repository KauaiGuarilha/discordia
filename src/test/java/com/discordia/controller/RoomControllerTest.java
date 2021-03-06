package com.discordia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.discordia.model.dto.*;
import com.discordia.model.factory.RoomDTOResponseFactory;
import com.discordia.model.factory.UserRoomsDTOResponseFactory;
import com.discordia.model.service.RoomService;
import com.discordia.utils.JsonMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class RoomControllerTest {

    private MockMvc mockMvc;

    @Mock private RoomService service;
    @Mock private RoomDTOResponseFactory roomFactory;
    @Mock private UserRoomsDTOResponseFactory userRoomsFactory;

    @InjectMocks private RoomController controller;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Should create a room")
    public void shouldCreateARoom() throws Exception {
        List<UserDTO> users = new ArrayList<>();
        UserDTO user = UserDTO.builder().build();
        RoomDTO room =
                RoomDTO.builder()
                        .roomName("Room")
                        .capacityLimit(5)
                        .userHost(user)
                        .users(users)
                        .build();

        doReturn(RoomDTO.builder().build()).when(service).createRoom(any(RoomDTO.class));
        // doReturn(RoomDTOResponse.builder().build()).when(roomFactory).dtoToResponse(any(RoomDTO.class));

        mockMvc.perform(
                        post("/room/create")
                                .content(JsonMapper.asJsonString(room))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should enter room by IdRoom")
    public void shouldEnterRoomByIdRoom() throws Exception {

        UserDTO userDTO = UserDTO.builder().name("User").password("123").mobileToken("123").build();

        doNothing().when(service).enterRoom(any(UserDTO.class), any(UUID.class));

        mockMvc.perform(
                        post("/room/enter-room/d37db77c-e3bc-413c-af67-2a91ef235eda")
                                .content(JsonMapper.asJsonString(userDTO))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should alter host by IdRoom")
    public void shouldAlterHostByIdRoom() throws Exception {

        UserDTO userChange = UserDTO.builder().build();
        UserDTO userHost = UserDTO.builder().build();
        ChangeUserDTO changeUserDTODTO =
                ChangeUserDTO.builder().userChange(userChange).userHost(userHost).build();

        doNothing().when(service).alterRoomHost(any(ChangeUserDTO.class), any(UUID.class));

        mockMvc.perform(
                        post("/room/alter-host/d37db77c-e3bc-413c-af67-2a91ef235eda")
                                .content(JsonMapper.asJsonString(changeUserDTODTO))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return room by IdRoom")
    public void shouldReturnRoomByIdRoom() throws Exception {
        doReturn(RoomDTO.builder().build()).when(service).returnInfoRoom(any(UUID.class));
        // doReturn(RoomDTOResponse.builder().build()).when(roomFactory).dtoToResponse(any(RoomDTO.class));

        mockMvc.perform(get("/room/return-room/d37db77c-e3bc-413c-af67-2a91ef235eda"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Should return user by room")
    public void shouldReturnUserByRoom() throws Exception {
        List<RoomDTO> rooms = new ArrayList<>();

        doReturn(rooms).when(service).returnRoomsByUser(any(String.class));
        // doReturn(RoomDTOResponse.builder().build()).when(roomFactory).dtoToResponse(any(RoomDTO.class));
        doReturn(UserRoomsDTOResponse.builder().build())
                .when(userRoomsFactory)
                .dtoToUserRooms(any(String.class), anyList());

        mockMvc.perform(get("/room/return-user-room/user"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
