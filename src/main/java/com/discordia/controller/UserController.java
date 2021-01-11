package com.discordia.controller;

import com.discordia.model.dto.UserDTO;
import com.discordia.model.dto.UserRegistryDTOResponse;
import com.discordia.model.factory.UserDTOResponseFactory;
import com.discordia.model.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private UserService service;
    @Autowired private UserDTOResponseFactory factory;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity saveUser(@RequestBody UserDTO dto) {
        UserRegistryDTOResponse response =
                factory.userToResponse(service.saveUser(factory.toUser(dto)));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public UserRegistryDTOResponse updateUser(@PathVariable String id, @RequestBody UserDTO dto) {
        return factory.userToResponse(service.update(factory.toUser(dto), id));
    }

    @GetMapping("/return/{username}")
    public UserRegistryDTOResponse ReturnUser(@NotBlank @PathVariable String username) {
        return factory.userToResponse(service.returnUser(username));
    }

    @GetMapping("/return")
    public UserRegistryDTOResponse ReturnUserQueryParam(@NotBlank @RequestParam String username) {
        return factory.userToResponse(service.returnUser(username));
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/return-list")
    public List<UserRegistryDTOResponse> ReturnListUsers() {
        return service.returnListUser().stream()
                .map(factory::userToResponse)
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        service.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
