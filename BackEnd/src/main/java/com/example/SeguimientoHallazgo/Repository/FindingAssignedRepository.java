package com.example.SeguimientoHallazgo.Repository;

import com.example.SeguimientoHallazgo.Domain.DTO.Projections.IFindingAssignedDTO;
import com.example.SeguimientoHallazgo.Domain.Finding.Finding;
import com.example.SeguimientoHallazgo.Domain.Finding.FindingAssigned;
import com.example.SeguimientoHallazgo.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FindingAssignedRepository extends JpaRepository<FindingAssigned, Long> {
    List<FindingAssigned> findAllByFinding_Id(int pFindingId);
    List<FindingAssigned> findAllByUser(User pUser);

    // Consulta para encontrar todos los FindingAssigned que tengan Finding y pertenezcan a un Center específico
    @Query(value = " SELECT fa.id, fa.work_order as workOrder, fa.date_end as dateEnd, fa.accept_finish as acceptFinish, fa.image_base64finish as imageBase64Finish, " +
            " u.full_name as userFullName, u.role as userRole, u.email as userEmail, " +
            " f.id as findingId,f.description, f.location,f.date_create as dateCreate, f.image_base64 as imageBase64, f.status, f.priority, " +
            " c.name as classification " +
            " FROM finding_assigned fa " +
            " join finding f on f.id=fa.finding_id " +
            " join classification c on c.id=f.classification_id " +
            " join user u on u.id=fa.user_id " +
            " WHERE f.center_id = :centerId ", nativeQuery = true)
    List<IFindingAssignedDTO> findAllByFindingCenterId(int centerId);
}