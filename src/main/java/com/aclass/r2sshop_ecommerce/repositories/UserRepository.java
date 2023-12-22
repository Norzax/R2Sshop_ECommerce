package com.aclass.r2sshop_ecommerce.repositories;

import com.aclass.r2sshop_ecommerce.models.entity.UserEntity;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select distinct u from UserEntity u left join fetch u.addressEntities")
    List<UserEntity> findAllByJoiningAddressEntity();

    @Query("select u from UserEntity u left join fetch u.addressEntities where u.id = :userId")
    UserEntity findByIdJoiningAddressEntity(@PathParam("user_id") Long userId);

    @Query("select u from UserEntity u where u.username = :username")
    Optional<UserEntity> findByUsername(@PathParam("username") String username);

    @Query("select distinct u from UserEntity u left join fetch u.addressEntities where u.username = :username")
    Optional<UserEntity> findByUsernameJoiningAddressEntity(@PathParam("username") String username);

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.status = true where u.status is null")
    void updateNullStatus();
}
