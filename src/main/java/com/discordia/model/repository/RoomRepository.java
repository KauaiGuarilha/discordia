package com.discordia.model.repository;

import com.discordia.model.entity.Room;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    // @Query("select r from Room r where r.name = ?1")
    @Query("SELECT r FROM Room r INNER JOIN User u ON u.name = ?1 ")
    Optional<List<Room>> findByUserName(String userName);
}
