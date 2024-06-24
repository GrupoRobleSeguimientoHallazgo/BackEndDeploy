package com.example.SeguimientoHallazgo.Service.Interface;

import com.example.SeguimientoHallazgo.Domain.Finding.Finding;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface FindingService {

    List<Finding> listAllFindingByCenter(int pCenterId);
    Finding getFindingInfo(Long pFindingId);
    Finding createFinding(int pCenterId, Finding pNewFinding);
    Finding updateFinding(Long pFindingId, Finding pNewFinding);
    Finding updateStatusFinding(Long pFindingId, String pNewStatus);
    Finding updatePriorityFinding(Long pFindingId, String pNewPriority);
}
