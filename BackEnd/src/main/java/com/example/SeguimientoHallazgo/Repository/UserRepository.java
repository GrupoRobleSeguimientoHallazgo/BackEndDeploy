package com.example.SeguimientoHallazgo.Repository;

import com.example.SeguimientoHallazgo.Domain.DTO.Projections.IUserDTO;
import com.example.SeguimientoHallazgo.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query(value = " SELECT u.id, u.email, u.full_name as fullName, u.role, c.id AS centerId " +
            " FROM user u " +
            " LEFT JOIN center c ON u.center_id = c.id " +
            " WHERE u.id = :id ", nativeQuery = true)
    IUserDTO findUserDTOById(Long id);

    @Query(value = " SELECT u.id, u.email, u.full_name as fullName, u.role, c.id AS centerId " +
            " FROM user u " +
            " LEFT JOIN center c ON u.center_id = c.id " +
            " WHERE c.id = :id ", nativeQuery = true)
    List<IUserDTO> findUserDTOByIdCenter(int id);
    @Query(value = " SELECT u.id, u.email, u.full_name as fullName, u.role, c.id AS centerId " +
            " FROM user u " +
            " WHERE U.id = :id ", nativeQuery = true)
    IUserDTO findUserDTOById(int id);
}