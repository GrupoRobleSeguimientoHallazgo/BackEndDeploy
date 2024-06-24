package com.example.SeguimientoHallazgo.Service.Impl;

import com.example.SeguimientoHallazgo.Domain.Center;
import com.example.SeguimientoHallazgo.Domain.Classification;
import com.example.SeguimientoHallazgo.Exceptions.NotFoundException;
import com.example.SeguimientoHallazgo.Repository.CenterRepository;
import com.example.SeguimientoHallazgo.Repository.ClassificationRepository;
import com.example.SeguimientoHallazgo.Service.Interface.ClassificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassificationServiceImpl implements ClassificationService {
    private final ClassificationRepository classificationRepository;
    private final CenterRepository centerRepository;

    /**
     * Obtiene todas las clasificaciones asociadas a un centro específico.
     *
     * @param pCenterId El ID del centro.
     * @return Una lista de clasificaciones asociadas al centro.
     */
    @Override
    public List<Classification> listAllClassificationsByCenter(Long pCenterId) {
        return classificationRepository.findAllByCenterId(pCenterId);
    }

    /**
     * Guarda una nueva clasificación asociada a un centro específico.
     *
     * @param pCenterId       El ID del centro.
     * @param pClassification La clasificación que se va a guardar.
     * @return La clasificación guardada.
     */
    @Override
    @Transactional
    public Classification saveClassification(int pCenterId, Classification pClassification) {
        // Verificar y obtener el centro
        Center center = centerRepository.findById(pCenterId)
                .orElseThrow(() -> new NotFoundException("No se encontró el centro solicitado"));

        // Asociar la clasificación al centro
        pClassification.setCenter(center);

        // Añadir la clasificación a la lista de clasificaciones del centro
       /* List<Classification> listClassification = center.getClassifications();
        listClassification.add(pClassification);

        // Guardar el centro y la clasificación
        centerRepository.save(center);*/
        return classificationRepository.save(pClassification);
    }

    /**
     * Elimina una clasificación específica por su ID.
     *
     * @param pClassificationId El ID de la clasificación que se va a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    @Override
    public boolean deleteClassification(Long pClassificationId) {
        try {
            classificationRepository.deleteById(pClassificationId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
