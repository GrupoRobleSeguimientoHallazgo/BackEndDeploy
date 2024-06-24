package com.example.SeguimientoHallazgo.Repository;

import com.example.SeguimientoHallazgo.Domain.Finding.Finding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FindingRepository extends JpaRepository<Finding, Long> {
    List<Finding> findAllByCenterId(int pCenterId);

    @Query("SELECT f FROM Finding f WHERE f.center.id = :pCenterId AND f.findingAssigned IS NULL")
    List<Finding> findAllByCenterIdWithoutFindingAssigned(int pCenterId);
}
