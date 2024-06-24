package com.example.SeguimientoHallazgo.Service.Interface;

import com.example.SeguimientoHallazgo.Domain.Material;

import java.util.List;

public interface MaterialService {

    List<Material> listAllMaterialByFindingAssigned(Long pFindingAssigned);
    Material saveMaterial(Long pFindingAssigned, Material pMaterial);
    Material updateMaterial(Long pMaterialId, Material pMaterial);
    boolean deleteMaterial(Long pMaterialId);
}
