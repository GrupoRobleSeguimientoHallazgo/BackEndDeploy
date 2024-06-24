package com.example.SeguimientoHallazgo.Repository;

import com.example.SeguimientoHallazgo.Domain.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClassificationRepository extends JpaRepository<Classification, Long> {
    List<Classification> findAllByCenterId(Long pCenterId);
}
