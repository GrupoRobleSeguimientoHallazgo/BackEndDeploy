package com.example.SeguimientoHallazgo.Repository;

import com.example.SeguimientoHallazgo.Domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByFindingAssigned_Id(Long pFindingAssignedId);
}
