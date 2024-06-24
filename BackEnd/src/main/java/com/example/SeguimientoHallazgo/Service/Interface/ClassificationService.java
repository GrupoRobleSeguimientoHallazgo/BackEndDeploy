package com.example.SeguimientoHallazgo.Service.Interface;

import com.example.SeguimientoHallazgo.Domain.Classification;
import com.example.SeguimientoHallazgo.Domain.Comment;

import java.util.List;

public interface ClassificationService {
    List<Classification> listAllClassificationsByCenter(Long pCenterId);
    Classification saveClassification(int pCenterId, Classification pClassification);
    boolean deleteClassification(Long pClassificationId);


}
