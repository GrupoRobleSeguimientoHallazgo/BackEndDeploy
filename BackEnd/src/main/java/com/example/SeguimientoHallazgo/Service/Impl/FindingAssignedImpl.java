package com.example.SeguimientoHallazgo.Service.Impl;

import com.example.SeguimientoHallazgo.Common.Priority;
import com.example.SeguimientoHallazgo.Common.Role;
import com.example.SeguimientoHallazgo.Common.Status;
import com.example.SeguimientoHallazgo.Domain.Center;
import com.example.SeguimientoHallazgo.Domain.Classification;
import com.example.SeguimientoHallazgo.Domain.DTO.Projections.IFindingAssignedDTO;
import com.example.SeguimientoHallazgo.Domain.Finding.Finding;
import com.example.SeguimientoHallazgo.Domain.Finding.FindingAssigned;
import com.example.SeguimientoHallazgo.Domain.Material;
import com.example.SeguimientoHallazgo.Domain.User;
import com.example.SeguimientoHallazgo.Exceptions.NotFoundException;
import com.example.SeguimientoHallazgo.Repository.CenterRepository;
import com.example.SeguimientoHallazgo.Repository.FindingAssignedRepository;
import com.example.SeguimientoHallazgo.Repository.FindingRepository;
import com.example.SeguimientoHallazgo.Repository.UserRepository;
import com.example.SeguimientoHallazgo.Service.Interface.FindingAssignedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindingAssignedImpl implements FindingAssignedService {

    private final FindingAssignedRepository findingAssignedRepository;
    private final UserRepository userRepository;
    private final FindingRepository findingRepository;
    private final CenterRepository centerRepository;

    /**
     * Recupera una lista de todos los hallazgos asignados a un centro dado su ID.
     *
     * @param pCenterId El ID del centro.
     * @return Una lista de hallazgos asignados al centro.
     * @throws NotFoundException Si el centro con el ID especificado no se encuentra.
     */
    @Override
    public List<FindingAssigned> listAllFindingByCenter(int pCenterId) {
        if (!centerRepository.existsById(pCenterId)) {
            throw new NotFoundException("No se encuentra el centro solicitado");
        }
        List<IFindingAssignedDTO> findingAssignedDTOS = findingAssignedRepository.findAllByFindingCenterId(pCenterId);
        List<FindingAssigned> findingAssignedList = new ArrayList<>();

        for (IFindingAssignedDTO dto : findingAssignedDTOS) {
            FindingAssigned findingAssigned = new FindingAssigned();

            //Datos del FindingAssigned
            findingAssigned.setId(dto.getId());
            findingAssigned.setWorkOrder(dto.getWorkOrder());
            findingAssigned.setDateEnd(dto.getDateEnd());
            findingAssigned.setAcceptFinish(dto.getAcceptFinish());
            findingAssigned.setImageBase64Finish(dto.getImageBase64Finish());

            //Datos del Finding
            Finding finding = new Finding();
            finding.setId(dto.getFindingId());
            finding.setStatus(Status.valueOf(dto.getStatus()));
            finding.setDateCreate(dto.getDateCreate());
            finding.setPriority(Priority.valueOf(dto.getPriority()));
            finding.setDescription(dto.getDescription());
            finding.setImageBase64(dto.getImageBase64());
            finding.setLocation(dto.getLocation());

            //Datos Classification
            Classification classification = new Classification();
            classification.setName(dto.getClassification());

            finding.setClassification(classification);
            findingAssigned.setFinding(finding);

            //Datos del User
            User user = new User();
            user.setFullName(dto.getUserFullName());
            user.setRole(Role.valueOf(dto.getUserRole()));
            user.setEmail(dto.getUserEmail());

            findingAssigned.setUser(user);

            findingAssignedList.add(findingAssigned);
        }

        return findingAssignedList;
    }

    /**
     * Recupera una lista de todos los hallazgos asignados a un usuario dado su ID.
     *
     * @param pUserId El ID del usuario.
     * @return Una lista de hallazgos asignados al usuario.
     * @throws NotFoundException Si el usuario con el ID especificado no se encuentra.
     */
    @Override
    public List<FindingAssigned> listAllFindingByUser(Long pUserId) {
        User user = userRepository.findById(pUserId)
                .orElseThrow(() -> new NotFoundException("No se encuentra el usuario solicitado"));

        return findingAssignedRepository.findAllByUser(user);
    }

    /**
     * Recupera la información de un hallazgo asignado dado su ID.
     *
     * @param pFindingId El ID del hallazgo.
     * @return La información del hallazgo asignado.
     * @throws NotFoundException Si el hallazgo con el ID especificado no se encuentra.
     */
    @Override
    public FindingAssigned getFindingInfoByFinding(Long pFindingId) {
        return findingAssignedRepository.findById(pFindingId)
                .orElseThrow(() -> new NotFoundException("No se encuentra los datos del hallazgo solicitado"));
    }

    /**
     * Crea y asigna un nuevo hallazgo a un usuario.
     *
     * @param pFindingId El ID del hallazgo.
     * @param pUserId El ID del usuario.
     * @param pFindingAssigned El hallazgo asignado que se va a crear.
     * @return El hallazgo asignado creado.
     * @throws NotFoundException Si el usuario o el hallazgo con los IDs especificados no se encuentran.
     */
    @Override
    @Transactional
    public FindingAssigned createFindingAssigned(Long pFindingId, Long pUserId, FindingAssigned pFindingAssigned) {
        User user = userRepository.findById(pUserId)
                .orElseThrow(() -> new NotFoundException("No se encuentra el usuario solicitado"));

        Finding finding = findingRepository.findById(pFindingId)
                .orElseThrow(() -> new NotFoundException("No se encuentra el hallazgo solicitado"));

        pFindingAssigned.setUser(user);
        pFindingAssigned.setFinding(finding);
        finding.setFindingAssigned(pFindingAssigned);
        finding.setStatus(Status.EN_PROGRESO);
        if (pFindingAssigned.getMaterials() != null) {
            pFindingAssigned.getMaterials().forEach(material -> material.setFindingAssigned(pFindingAssigned));
        }

        return findingAssignedRepository.save(pFindingAssigned);
    }

    /**
     * Marca un hallazgo como aceptado por el usuario.
     *
     * @param pFindingAssignedId El id hallazgo asignado que se va a actualizar.
     * @return El hallazgo asignado actualizado.
     * @throws NotFoundException Si el hallazgo asociado no se encuentra.
     */
    @Override
    @Transactional
    public FindingAssigned userFinishFinding(Long pFindingAssignedId, FindingAssigned pNewFindingAssigned) {
        FindingAssigned findingAssigned = findingAssignedRepository.findById(pFindingAssignedId)
                .orElseThrow(() -> new NotFoundException("No se encuentra el hallazgo solicitado"));

        findingAssigned.setImageBase64Finish(pNewFindingAssigned.getImageBase64Finish());
        findingAssigned.setDateEnd(pNewFindingAssigned.getDateEnd());
        findingAssigned.setAcceptFinish(true);

        Finding finding = findingRepository.findById(findingAssigned.getFinding().getId())
                .orElseThrow(() -> new NotFoundException("No se encuentra el hallazgo solicitado"));

        finding.setStatus(Status.ESPERA);
        findingRepository.save(finding);

        return findingAssignedRepository.save(findingAssigned);
    }

    /**
     * Marca un hallazgo como completado por el administrador.
     *
     * @param pFindingAssignedId El id de hallazgo asignado que se va a actualizar.
     * @return El hallazgo asignado actualizado.
     * @throws NotFoundException Si el hallazgo asociado no se encuentra.
     */
    @Override
    @Transactional
    public FindingAssigned adminFinishFinding(Long pFindingAssignedId) {
        FindingAssigned findingAssigned = findingAssignedRepository.findById(pFindingAssignedId)
                .orElseThrow(() -> new NotFoundException("No se encuentra el hallazgo solicitado"));

        Finding finding = findingRepository.findById(findingAssigned.getFinding().getId())
                .orElseThrow(() -> new NotFoundException("No se encuentra el hallazgo solicitado"));

        finding.setStatus(Status.COMPLETADO);
        findingRepository.save(finding);

        return findingAssignedRepository.save(findingAssigned);
    }
}