package com.example.SeguimientoHallazgo.Service.Impl;

import com.example.SeguimientoHallazgo.Domain.Center;
import com.example.SeguimientoHallazgo.Domain.DTO.EmailDTO;
import com.example.SeguimientoHallazgo.Domain.DTO.Projections.IUserDTO;
import com.example.SeguimientoHallazgo.Domain.User;
import com.example.SeguimientoHallazgo.Exceptions.NotFoundException;
import com.example.SeguimientoHallazgo.Repository.CenterRepository;
import com.example.SeguimientoHallazgo.Repository.UserRepository;
import com.example.SeguimientoHallazgo.Service.Interface.UserService;
import com.example.SeguimientoHallazgo.Util.Email.EmailServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CenterRepository centerRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;

    /**
     * Recupera una lista de usuarios asociados a un centro específico.
     *
     * @param pCenterId El ID del centro.
     * @return Una lista de usuarios.
     * @throws NotFoundException Si el centro no se encuentra.
     */
    @Override
    public List<IUserDTO> listAllUsersByCenter(int pCenterId) {
        if (!centerRepository.existsById(pCenterId)) {
            throw new NotFoundException("No se encuentra el centro solicitado");
        }

        return userRepository.findUserDTOByIdCenter(pCenterId);
    }

    /**
     * Recupera la información de un usuario específico.
     *
     * @param pUserId El ID del usuario.
     * @return La información del usuario.
     * @throws NotFoundException Si el usuario no se encuentra.
     */
    @Override
    @Transactional(readOnly = true)
    public IUserDTO getUserInfo(Long pUserId) {
        return userRepository.findUserDTOById(pUserId);
    }

    /**
     * Registra un nuevo usuario en un centro específico.
     *
     * @param pCenterId El ID del centro.
     * @param pNewUser  El nuevo usuario que se va a registrar.
     * @return El usuario registrado.
     * @throws NotFoundException Si el centro no se encuentra.
     */
    @Override
    @Transactional
    public IUserDTO registerUser(int pCenterId, User pNewUser) throws MessagingException {
        Center center = centerRepository.findById(pCenterId)
                .orElseThrow(() -> new NotFoundException("No se encontró el centro seleccionado"));

        String generatedPassword = generateRandomPassword();
        sendEmail(pNewUser.getEmail(), pNewUser.getFullName(), generatedPassword);

        pNewUser.setPassword(passwordEncoder.encode(generatedPassword));
        pNewUser.setCenter(center);

        User newUser = userRepository.save(pNewUser);

        IUserDTO userDTO = userRepository.findUserDTOById(newUser.getId());


        return userDTO;
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param pUserId   El ID del usuario.
     * @param pNewUser  El usuario con la información actualizada.
     * @return El usuario actualizado.
     * @throws NotFoundException Si el usuario no se encuentra.
     */
    @Override
    @Transactional
    public User updateUser(Long pUserId, User pNewUser) {
        User user = userRepository.findById(pUserId)
                .orElseThrow(() -> new NotFoundException("No se encontró el usuario seleccionado"));

        user.setRole(pNewUser.getRole());
        user.setFullName(pNewUser.getFullName());

        return userRepository.save(user);
    }

    private String generateRandomPassword() {
        // Genera una contraseña aleatoria de 8 a 12 caracteres
        return RandomStringUtils.randomAlphanumeric(10); // Puedes cambiar el tamaño y los caracteres según tus necesidades
    }

    private void sendEmail(String pToDestination, String pRecipientName, String pPassword) throws MessagingException {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmailTitle("Registro al sistema");
        emailDTO.setRecipientName(pRecipientName);
        emailDTO.setToDestination(pToDestination);
        String texto="La contraseña autogenerada es: " +pPassword;
        emailDTO.setEmailBody(texto);
       emailService.sendMail(emailDTO);
    }
}