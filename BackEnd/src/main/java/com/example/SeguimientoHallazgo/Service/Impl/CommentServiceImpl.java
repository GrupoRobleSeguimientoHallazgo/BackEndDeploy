package com.example.SeguimientoHallazgo.Service.Impl;

import com.example.SeguimientoHallazgo.Domain.Comment;
import com.example.SeguimientoHallazgo.Domain.Finding.FindingAssigned;
import com.example.SeguimientoHallazgo.Exceptions.NotFoundException;
import com.example.SeguimientoHallazgo.Repository.CommentRepository;
import com.example.SeguimientoHallazgo.Repository.FindingAssignedRepository;
import com.example.SeguimientoHallazgo.Service.Interface.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final FindingAssignedRepository findingAssignedRepository;

    /**
     * Lista todos los comentarios asociados a un FindingAssigned específico.
     *
     * @param pFindingAssigned El ID del FindingAssigned.
     * @return Una lista de comentarios.
     */
    @Override
    public List<Comment> listAllCommentsByFindingAssigned(Long pFindingAssigned) {
        return commentRepository.findAllByFindingAssigned_Id(pFindingAssigned);
    }

    /**
     * Guarda un comentario asociado a un FindingAssigned específico.
     *
     * @param pFindingAssigned El ID del FindingAssigned.
     * @param pComment El comentario a guardar.
     * @return El comentario guardado.
     */
    @Override
    @Transactional
    public Comment saveComment(Long pFindingAssigned, Comment pComment) {
        // Verificar y obtener el FindingAssigned
        FindingAssigned findingAssigned = findingAssignedRepository.findById(pFindingAssigned)
                .orElseThrow(() -> new NotFoundException("No se encontró el hallazgo solicitado"));

        // Establecer la relación entre el comentario y el FindingAssigned
        pComment.setFindingAssigned(findingAssigned);

        // Agregar el comentario a la lista de comentarios del FindingAssigned
        /*List<Comment> listComments = findingAssigned.getComments();
        listComments.add(pComment);

        // Guardar el FindingAssigned actualizado
        findingAssignedRepository.save(findingAssigned);*/

        // Guardar y retornar el comentario
        return commentRepository.save(pComment);
    }

    /**
     * Elimina un comentario.
     *
     * @param pCommentId El id del comentario a eliminar.
     * @return true si se eliminó correctamente, false si ocurrió un error.
     */
    @Override
    @Transactional
    public boolean deleteComment(Long pCommentId) {
        try {
            commentRepository.deleteById(pCommentId);
            return true;
        } catch (Exception e) {
            logger.error("Error eliminando comentario", e);
            return false;
        }
    }
}
