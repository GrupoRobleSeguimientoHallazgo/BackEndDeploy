package com.example.SeguimientoHallazgo.Service.Impl;


import com.example.SeguimientoHallazgo.Domain.Center;
import com.example.SeguimientoHallazgo.Exceptions.DuplicateException;
import com.example.SeguimientoHallazgo.Exceptions.NotFoundException;
import com.example.SeguimientoHallazgo.Repository.CenterRepository;
import com.example.SeguimientoHallazgo.Service.Interface.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {

    private final CenterRepository centerRepository;

    /**
     * Recupera una lista de todos los centros disponibles.
     *
     * @return Una lista que contiene todos los centros.
     */
    @Override
    public List<Center> listAllCenter() {
        return centerRepository.findAll();
    }

    /**
     * Obtiene la información de un centro dado su ID.
     *
     * @param pCenterId El ID del centro.
     * @return La información del centro si se encuentra.
     * @throws NotFoundException Si el centro con el ID especificado no se encuentra.
     */
    @Override
    public Center getCenterInfo(int pCenterId) {
        return centerRepository.findById(pCenterId)
                .orElseThrow(() -> new NotFoundException("No se encontró el centro solicitado"));
    }

    /**
     * Crea un nuevo centro.
     *
     * @param pNewCenter El centro que se va a crear.
     * @return El centro creado.
     * @throws DuplicateException Si el código del centro ya existe en la base de datos.
     */
    @Transactional
    @Override
    public Center createCenter(Center pNewCenter) {
        try {
            return centerRepository.save(pNewCenter);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateException("Center code must be unique: " + pNewCenter.getCode());
        }
    }

    /**
     * Actualiza la información de un centro dado su ID. Por el momento solo actualiza la descripción.
     *
     * @param pCenterId   El ID del centro que se va a actualizar.
     * @param pNewCenter  El nuevo centro con la información actualizada.
     * @return El centro actualizado.
     * @throws NotFoundException Si el centro con el ID especificado no se encuentra.
     */
    @Transactional
    @Override
    public Center updateCenter(int pCenterId, Center pNewCenter) {
        return centerRepository.findById(pCenterId)
                .map(existingCenter -> {
                    existingCenter.setDescription(pNewCenter.getDescription());
                    return centerRepository.save(existingCenter);
                })
                .orElseThrow(() -> new NotFoundException("Center with ID " + pCenterId + " not found"));
    }
}