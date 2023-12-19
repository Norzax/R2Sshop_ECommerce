package com.aclass.r2sshop_ecommerce.repositories;

import com.aclass.r2sshop_ecommerce.models.entity.UserEntity;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u where u.username = :username")
    Optional<UserEntity> findByUsername(@PathParam("username") String username);
}
