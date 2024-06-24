package com.example.SeguimientoHallazgo.Service.Interface;

import com.example.SeguimientoHallazgo.Domain.DTO.Projections.IUserDTO;
import com.example.SeguimientoHallazgo.Domain.User;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserService {

    List<IUserDTO> listAllUsersByCenter(int pCenterId);
    IUserDTO getUserInfo(Long pUserId);
    IUserDTO registerUser(int pCenterId, User pNewUser) throws MessagingException;
    User updateUser(Long pUserId, User pNewUser);

}
