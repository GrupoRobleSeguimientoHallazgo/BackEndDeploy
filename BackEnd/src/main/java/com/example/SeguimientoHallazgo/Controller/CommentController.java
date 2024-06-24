package com.example.SeguimientoHallazgo.Controller;

import com.example.SeguimientoHallazgo.Domain.Center;
import com.example.SeguimientoHallazgo.Domain.Comment;
import com.example.SeguimientoHallazgo.Exceptions.NotFoundException;
import com.example.SeguimientoHallazgo.Service.Interface.CenterService;
import com.example.SeguimientoHallazgo.Service.Interface.CommentService;
import com.example.SeguimientoHallazgo.Util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * Obtiene todos los comentarios asociados a un hallazgo asignado específico.
     *
     * @param pFindingAssignedId El ID del hallazgo asignado.
     * @return Una respuesta HTTP que contiene la lista de comentarios y el estado de la solicitud.
     */
    @GetMapping("/findingAssigned/{pFindingAssignedId}")
    public ResponseEntity<Response<List<Comment>>> getAllCommentsByFindingAssingedId(@PathVariable Long pFindingAssignedId) {
        try {
            List<Comment> comments = commentService.listAllCommentsByFindingAssigned(pFindingAssignedId);
            Response<List<Comment>> response = Response.<List<Comment>>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(comments)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Response<List<Comment>> response = Response.<List<Comment>>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Guarda un nuevo comentario asociado a un hallazgo asignado específico.
     *
     * @param pFindingAssignedId El ID del hallazgo asignado.
     * @param pComment           El comentario que se va a guardar.
     * @return Una respuesta HTTP que contiene el comentario guardado y el estado de la solicitud.
     */
    @PostMapping("/findingAssigned/{pFindingAssignedId}")
    public ResponseEntity<Response<Comment>> saveComment(@PathVariable Long pFindingAssignedId, @RequestBody Comment pComment) {
        try {
            Comment comment = commentService.saveComment(pFindingAssignedId, pComment);
            Response<Comment> response = Response.<Comment>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(comment)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (NotFoundException e){
            Response<Comment> response = Response.<Comment>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Response<Comment> response = Response.<Comment>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un comentario específico.
     *
     * @param pCommentId El ID del comentario que se va a eliminar.
     * @return Una respuesta HTTP que contiene el resultado de la operación y el estado de la solicitud.
     */
    @DeleteMapping("/{pCommentId}")
    public ResponseEntity<Response<Boolean>> deleteComment(@PathVariable Long pCommentId) {
        try {
            boolean commentDeleted = commentService.deleteComment(pCommentId);
            Response<Boolean> response = Response.<Boolean>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(commentDeleted)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Response<Boolean> response = Response.<Boolean>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}