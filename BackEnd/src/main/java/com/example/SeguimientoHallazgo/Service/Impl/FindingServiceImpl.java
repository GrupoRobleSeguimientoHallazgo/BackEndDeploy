package com.example.SeguimientoHallazgo.Service.Impl;

import com.example.SeguimientoHallazgo.Common.Priority;
import com.example.SeguimientoHallazgo.Common.Status;
import com.example.SeguimientoHallazgo.Domain.Center;
import com.example.SeguimientoHallazgo.Domain.Finding.Finding;
import com.example.SeguimientoHallazgo.Exceptions.NotFoundException;
import com.example.SeguimientoHallazgo.Repository.CenterRepository;
import com.example.SeguimientoHallazgo.Repository.FindingRepository;
import com.example.SeguimientoHallazgo.Service.Interface.FindingService;
import com.example.SeguimientoHallazgo.Util.CloudinaryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindingServiceImpl implements FindingService {

    private final FindingRepository findingRepository;
    private final CenterRepository centerRepository;
    private final CloudinaryUtil cloudinaryUtil;

    /**
     * Recupera una lista de hallazgos para un centro específico que no tienen hallazgos asignados.
     *
     * @param pCenterId El ID del centro.
     * @return Una lista de hallazgos.
     * @throws NotFoundException Si no se encuentran hallazgos en el centro solicitado.
     */
    @Override
    public List<Finding> listAllFindingByCenter(int pCenterId) {
        List<Finding> findings = findingRepository.findAllByCenterIdWithoutFindingAssigned(pCenterId);
        if (findings.isEmpty()) {
            throw new NotFoundException("No se encontraron hallazgos en el centro solicitado");
        }
        return findings;
    }

    /**
     * Recupera la información de un hallazgo dado su ID.
     *
     * @param pFindingId El ID del hallazgo.
     * @return El hallazgo correspondiente.
     * @throws NotFoundException Si el hallazgo no se encuentra.
     */
    @Override
    public Finding getFindingInfo(Long pFindingId) {
        return findingRepository.findById(pFindingId)
                .orElseThrow(() -> new NotFoundException("No se encuentra información del hallazgo solicitado"));
    }

    /**
     * Crea un nuevo hallazgo en un centro específico.
     *
     * @param pCenterId   El ID del centro.
     * @param pNewFinding El nuevo hallazgo que se va a crear.
     * @return El hallazgo creado.
     * @throws NotFoundException Si el centro no se encuentra.
     */
    @Override
    @Transactional
    public Finding createFinding(int pCenterId, Finding pNewFinding) {
        Center center = centerRepository.findById(pCenterId)
                .orElseThrow(() -> new NotFoundException("No se encontró el centro seleccionado"));

        pNewFinding.setCenter(center);
        pNewFinding.setDateCreate(new Date());

        return findingRepository.save(pNewFinding);
    }

    /**
     * Actualiza la información de un hallazgo existente.
     *
     * @param pFindingId  El ID del hallazgo.
     * @param pNewFinding El hallazgo actualizado.
     * @return El hallazgo actualizado.
     * @throws NotFoundException Si el hallazgo no se encuentra.
     */
    @Override
    @Transactional
    public Finding updateFinding(Long pFindingId, Finding pNewFinding) {
        Finding existingFinding = findingRepository.findById(pFindingId)
                .orElseThrow(() -> new NotFoundException("No se encontró el hallazgo solicitado"));


        return findingRepository.save(existingFinding);
    }

    /**
     * Actualiza el estado de un hallazgo existente.
     *
     * @param pFindingId El ID del hallazgo.
     * @param pNewStatus El nuevo estado del hallazgo.
     * @return El hallazgo actualizado.
     * @throws NotFoundException Si el hallazgo no se encuentra.
     */
    @Override
    @Transactional
    public Finding updateStatusFinding(Long pFindingId, String pNewStatus) {
        Finding findingToUpdate = findingRepository.findById(pFindingId)
                .orElseThrow(() -> new NotFoundException("No se encontró el hallazgo con ID: " + pFindingId));

        Status newStatus = Status.valueOf(pNewStatus.toUpperCase());
        findingToUpdate.setStatus(newStatus);

        return findingRepository.save(findingToUpdate);
    }

    /**
     * Actualiza la prioridad de un hallazgo existente.
     *
     * @param pFindingId   El ID del hallazgo.
     * @param pNewPriority La nueva prioridad del hallazgo.
     * @return El hallazgo actualizado.
     * @throws NotFoundException Si el hallazgo no se encuentra.
     */
    @Override
    @Transactional
    public Finding updatePriorityFinding(Long pFindingId, String pNewPriority) {
        Finding findingToUpdate = findingRepository.findById(pFindingId)
                .orElseThrow(() -> new NotFoundException("No se encontró el hallazgo con ID: " + pFindingId));

        Priority newPriority = Priority.valueOf(pNewPriority.toUpperCase());
        findingToUpdate.setPriority(newPriority);

        return findingRepository.save(findingToUpdate);
    }
}