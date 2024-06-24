package com.example.SeguimientoHallazgo.Domain.DTO.Projections;

import java.util.Date;

public interface IFindingAssignedDTO {
    //FindingAssigned
    Long getId();
    String getWorkOrder();
    Date getDateEnd();
    Boolean getAcceptFinish();
    String getImageBase64Finish();
    //Finding
    Long getFindingId();
    String getDescription();
    String getLocation();
    Date getDateCreate();
    String getClassification();
    String getImageBase64();
    String getStatus();
    String getPriority();
    //User
    String getUserFullName();
    String getUserRole();
    String getUserEmail();

}
