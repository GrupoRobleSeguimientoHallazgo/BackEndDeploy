package com.example.SeguimientoHallazgo.Service.Impl;

import com.example.SeguimientoHallazgo.Domain.Center;
import com.example.SeguimientoHallazgo.Domain.Finding.FindingAssigned;
import com.example.SeguimientoHallazgo.Domain.Material;
import com.example.SeguimientoHallazgo.Exceptions.NotFoundException;
import com.example.SeguimientoHallazgo.Repository.FindingAssignedRepository;
import com.example.SeguimientoHallazgo.Repository.MaterialRepository;
import com.example.SeguimientoHallazgo.Service.Interface.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final FindingAssignedRepository findingAssignedRepository;

    /**
     * Recupera una lista de materiales asociados a un hallazgo asignado específico.
     *
     * @param pFindingAssigned El ID del hallazgo asignado.
     * @return Una lista de materiales.
     * @throws NotFoundException Si el hallazgo asignado no se encuentra.
     */
    @Override
    public List<Material> listAllMaterialByFindingAssigned(Long pFindingAssigned) {
        findingAssignedRepository.findById(pFindingAssigned)
                .orElseThrow(() -> new NotFoundException("No se encontró el hallazgo seleccionado"));

        return materialRepository.findAllByFindingAssigned_Id(pFindingAssigned);
    }

    /**
     * Guarda un nuevo material asociado a un hallazgo asignado.
     *
     * @param pFindingAssigned El ID del hallazgo asignado.
     * @param pMaterial        El material que se va a guardar.
     * @return El material guardado.
     * @throws NotFoundException Si el hallazgo asignado no se encuentra.
     */
    @Override
    @Transactional
    public Material saveMaterial(Long pFindingAssigned, Material pMaterial) {
        FindingAssigned findingAssigned = findingAssignedRepository.findById(pFindingAssigned)
                .orElseThrow(() -> new NotFoundException("No se encontró el hallazgo seleccionado"));

        pMaterial.setFindingAssigned(findingAssigned);
        return materialRepository.save(pMaterial);
    }

    /**
     * Actualiza la información de un material existente.
     *
     * @param pMaterialId El ID del material.
     * @param pMaterial   El material con la información actualizada.
     * @return El material actualizado.
     * @throws NotFoundException Si el material no se encuentra.
     */
    @Override
    @Transactional
    public Material updateMaterial(Long pMaterialId, Material pMaterial) {
        Material existingMaterial = materialRepository.findById(pMaterialId)
                .orElseThrow(() -> new NotFoundException("No se encontró el material seleccionado"));

        // Actualizar solo los campos necesarios
        existingMaterial.setQuantity(pMaterial.getQuantity());
        existingMaterial.setName(pMaterial.getName());

        return materialRepository.save(existingMaterial);
    }

    /**
     * Elimina un material dado su ID.
     *
     * @param pMaterialId El ID del material.
     * @return true si el material fue eliminado correctamente.
     * @throws NotFoundException Si el material no se encuentra.
     */
    @Override
    @Transactional
    public boolean deleteMaterial(Long pMaterialId) {
        Material material = materialRepository.findById(pMaterialId)
                .orElseThrow(() -> new NotFoundException("No se encontró el material seleccionado"));

        materialRepository.delete(material);
        return true;
    }
}