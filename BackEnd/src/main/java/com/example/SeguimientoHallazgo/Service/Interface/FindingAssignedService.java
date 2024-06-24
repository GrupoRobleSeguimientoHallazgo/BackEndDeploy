package com.example.SeguimientoHallazgo.Service.Interface;

import com.example.SeguimientoHallazgo.Domain.Finding.FindingAssigned;

import java.util.List;

public interface FindingAssignedService {
    List<FindingAssigned> listAllFindingByCenter(int pCenterId);
    List<FindingAssigned> listAllFindingByUser(Long pUserId);
    FindingAssigned getFindingInfoByFinding(Long pFindingId);
    FindingAssigned createFindingAssigned(Long pFindingId ,Long pUserId, FindingAssigned pFindingAssigned);
    FindingAssigned userFinishFinding(Long pFindingAssignedId, FindingAssigned pNewFindingAssigned);
    FindingAssigned adminFinishFinding(Long pFindingAssignedId);
}
