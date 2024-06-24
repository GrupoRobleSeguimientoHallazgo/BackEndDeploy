package com.example.SeguimientoHallazgo.Domain.DTO.Projections;

public interface IFindingDTO {
    String getDescription();
    String getLocation();
    String getDateCreate();
    String getClassification();
    String getImageBase64();
    String getStatus();
    String getPriority();
}
