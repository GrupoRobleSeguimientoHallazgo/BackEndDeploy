package com.example.SeguimientoHallazgo.Controller;

import com.example.SeguimientoHallazgo.Domain.Finding.Finding;
import com.example.SeguimientoHallazgo.Exceptions.NotFoundException;
import com.example.SeguimientoHallazgo.Service.Interface.FindingService;
import com.example.SeguimientoHallazgo.Util.Response;
import com.example.SeguimientoHallazgo.Util.EnumsCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/finding")
@RequiredArgsConstructor
public class FindingController {

    private final FindingService findingService;
    private final EnumsCheck enumsCheck;



    /**
     * Endpoint para obtener todos los hallazgos de un centro específico por su ID.
     *
     * @param pCenterId El ID del centro cuyos hallazgos se van a consultar.
     * @return ResponseEntity con la respuesta que incluye la lista de hallazgos encontrados.
     */
    @GetMapping("/center/{pCenterId}")
    public ResponseEntity<Response<List<Finding>>> getAllFindingsByCenterId(@PathVariable int pCenterId) {
        try {
            // Llama al servicio para obtener la lista de hallazgos del centro especificado.
            List<Finding> findings = findingService.listAllFindingByCenter(pCenterId);

            // Retorna una respuesta exitosa con la lista de hallazgos.
            Response<List<Finding>> response = Response.<List<Finding>>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(findings)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el centro, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<List<Finding>> response = Response.<List<Finding>>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<List<Finding>> response = Response.<List<Finding>>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para obtener la información de un hallazgo específico por su ID.
     *
     * @param pFindingId El ID del hallazgo que se va a consultar.
     * @return ResponseEntity con la respuesta que incluye el hallazgo encontrado.
     */
    @GetMapping("/{pFindingId}")
    public ResponseEntity<Response<Finding>> getFindingById(@PathVariable long pFindingId) {
        try {
            // Llama al servicio para obtener la información del hallazgo.
            Finding finding = findingService.getFindingInfo(pFindingId);

            // Retorna una respuesta exitosa con el hallazgo encontrado.
            Response<Finding> response = Response.<Finding>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(finding)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el hallazgo, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<Finding> response = Response.<Finding>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<Finding> response = Response.<Finding>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/center/{pCenterId}")
    public ResponseEntity<Response<Finding>> createFinding(@PathVariable int pCenterId, @RequestBody Finding pNewFinding) {
        try {
            // Llama al servicio para crear el hallazgo.
            Finding finding = findingService.createFinding(pCenterId, pNewFinding);

            // Retorna una respuesta exitosa con el hallazgo creado.
            Response<Finding> response = Response.<Finding>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(finding)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el centro, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<Finding> response = Response.<Finding>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<Finding> response = Response.<Finding>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Falta el de actualizar todo

    /**
     * Endpoint para actualizar el estado de un hallazgo.
     *
     * @param pFindingId El ID del hallazgo que se va a actualizar.
     * @param pNewStatus El nuevo estado que se va a asignar al hallazgo.
     * @return ResponseEntity con la respuesta que incluye el hallazgo actualizado.
     */
    @PutMapping("/{pFindingId}/status/{pNewStatus}")
    public ResponseEntity<Response<Finding>> updateFindingStatus(@PathVariable Long pFindingId, @PathVariable String pNewStatus) {
        // Verifica si el estado proporcionado es válido.
        if (!enumsCheck.isValidStatus(pNewStatus)) {
            // Si el estado no es válido, retorna una respuesta con un estado HTTP 400 (Bad Request).
            Response<Finding> response = Response.<Finding>builder()
                    .status("error")
                    .message("Invalid status: " + pNewStatus)
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            // Llama al servicio para actualizar el estado del hallazgo.
            Finding finding = findingService.updateStatusFinding(pFindingId, pNewStatus);

            // Retorna una respuesta exitosa con el hallazgo actualizado.
            Response<Finding> response = Response.<Finding>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(finding)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el hallazgo, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<Finding> response = Response.<Finding>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<Finding> response = Response.<Finding>builder()
                    .status("error")
                    .message("An unexpected error occurred")
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para actualizar la prioridad de un hallazgo.
     *
     * @param pFindingId   El ID del hallazgo que se va a actualizar.
     * @param pNewPriority La nueva prioridad que se va a asignar al hallazgo.
     * @return ResponseEntity con la respuesta que incluye el hallazgo actualizado.
     */
    @PutMapping("/{pFindingId}/priority/{pNewPriority}")
    public ResponseEntity<Response<Finding>> updateFindingPriority(@PathVariable Long pFindingId, @PathVariable String pNewPriority) {
        // Verifica si la prioridad proporcionada es válida.
        if (!enumsCheck.isValidPriority(pNewPriority)) {
            // Si la prioridad no es válida, retorna una respuesta con un estado HTTP 400 (Bad Request).
            Response<Finding> response = Response.<Finding>builder()
                    .status("error")
                    .message("Invalid priority: " + pNewPriority)
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            // Llama al servicio para actualizar la prioridad del hallazgo.
            Finding finding = findingService.updatePriorityFinding(pFindingId, pNewPriority);

            // Retorna una respuesta exitosa con el hallazgo actualizado.
            Response<Finding> response = Response.<Finding>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(finding)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el hallazgo, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<Finding> response = Response.<Finding>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<Finding> response = Response.<Finding>builder()
                    .status("error")
                    .message("An unexpected error occurred")
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
