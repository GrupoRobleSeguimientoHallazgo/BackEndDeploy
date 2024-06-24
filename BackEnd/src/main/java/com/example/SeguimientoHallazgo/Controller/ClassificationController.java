package com.example.SeguimientoHallazgo.Controller;

import com.example.SeguimientoHallazgo.Domain.Classification;
import com.example.SeguimientoHallazgo.Domain.Comment;
import com.example.SeguimientoHallazgo.Exceptions.NotFoundException;
import com.example.SeguimientoHallazgo.Service.Interface.ClassificationService;
import com.example.SeguimientoHallazgo.Util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classification")
@RequiredArgsConstructor
public class ClassificationController {

    private final ClassificationService classificationService;

    /**
     * Obtiene todas las clasificaciones asociadas a un centro por su ID.
     *
     * @param pCenterId El ID del centro.
     * @return ResponseEntity con la lista de clasificaciones y el estado HTTP correspondiente.
     */
    @GetMapping("/center/{pCenterId}")
    public ResponseEntity<Response<List<Classification>>> getAllClassificationsByCenterId(@PathVariable Long pCenterId) {
        try {
            List<Classification> classifications = classificationService.listAllClassificationsByCenter(pCenterId);
            Response<List<Classification>> response = Response.<List<Classification>>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(classifications)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Response<List<Classification>> response = Response.<List<Classification>>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Guarda una nueva clasificación asociada a un centro.
     *
     * @param pCenterId       El ID del centro.
     * @param pClassification La clasificación que se va a guardar.
     * @return ResponseEntity con la clasificación guardada y el estado HTTP correspondiente.
     */
    @PostMapping("/center/{pCenterId}")
    public ResponseEntity<Response<Classification>> saveClassification(@PathVariable Long pCenterId, @RequestBody Classification pClassification) {
        try {
            Classification classification = classificationService.saveClassification(pCenterId.intValue(), pClassification);
            Response<Classification> response = Response.<Classification>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(classification)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (NotFoundException e) {
            Response<Classification> response = Response.<Classification>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            Response<Classification> response = Response.<Classification>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina una clasificación por su ID.
     *
     * @param pClassificationId El ID de la clasificación que se va a eliminar.
     * @return ResponseEntity con el resultado de la eliminación y el estado HTTP correspondiente.
     */
    @DeleteMapping("/{pClassificationId}")
    public ResponseEntity<Response<Boolean>> deleteClassification(@PathVariable Long pClassificationId) {
        try {
            boolean classificationDeleted = classificationService.deleteClassification(pClassificationId);
            Response<Boolean> response = Response.<Boolean>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(classificationDeleted)
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
