package com.example.SeguimientoHallazgo.Controller;

import com.example.SeguimientoHallazgo.Domain.Finding.FindingAssigned;
import com.example.SeguimientoHallazgo.Exceptions.NotFoundException;
import com.example.SeguimientoHallazgo.Service.Interface.FindingAssignedService;
import com.example.SeguimientoHallazgo.Util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/findingAssigned")
@RequiredArgsConstructor
public class FindingAssignedController {

    private final FindingAssignedService findingAssignedService;


    /**
     * Endpoint para obtener todos los hallazgos asignados a un centro específico.
     *
     * @param pCenterId el ID del centro cuyo hallazgos asignados se desean obtener.
     * @return una ResponseEntity con un Response que contiene la lista de hallazgos asignados encontrados o un mensaje de error.
     */
    @GetMapping("/center/{pCenterId}")
    public ResponseEntity<Response<List<FindingAssigned>>> getAllFindingsAssignedByCenter(@PathVariable int pCenterId) {
        try {
            // Llama al servicio para obtener la lista de hallazgos asignados del centro especificado.
            List<FindingAssigned> findingAssigneds = findingAssignedService.listAllFindingByCenter(pCenterId);

            // Retorna una respuesta exitosa con la lista de hallazgos asignados.
            Response<List<FindingAssigned>> response = Response.<List<FindingAssigned>>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(findingAssigneds)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el usuario, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<List<FindingAssigned>> response = Response.<List<FindingAssigned>>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<List<FindingAssigned>> response = Response.<List<FindingAssigned>>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para obtener todos los hallazgos asignados a un usuario específico.
     *
     * @param pUserId el ID del usuario cuyo hallazgos asignados se desean obtener.
     * @return una ResponseEntity con un Response que contiene la lista de hallazgos asignados encontrados o un mensaje de error.
     */
    @GetMapping("/user/{pUserId}")
    public ResponseEntity<Response<List<FindingAssigned>>> getAllFindingsAssignedByUser(@PathVariable long pUserId) {
        try {
            // Llama al servicio para obtener la lista de hallazgos asignados del usuario especificado.
            List<FindingAssigned> findingAssigneds = findingAssignedService.listAllFindingByUser(pUserId);

            // Retorna una respuesta exitosa con la lista de hallazgos asignados.
            Response<List<FindingAssigned>> response = Response.<List<FindingAssigned>>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(findingAssigneds)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el usuario, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<List<FindingAssigned>> response = Response.<List<FindingAssigned>>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<List<FindingAssigned>> response = Response.<List<FindingAssigned>>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para obtener la información de un hallazgo asignado por su ID.
     *
     * @param pFindingAssignedId el ID del hallazgo asignado que se desea obtener.
     * @return una ResponseEntity con un Response que contiene el hallazgo asignado encontrado o un mensaje de error.
     */
    @GetMapping("/{pFindingAssignedId}")
    public ResponseEntity<Response<FindingAssigned>> getFindingAssignedById(@PathVariable long pFindingAssignedId) {
        try {
            // Llama al servicio para obtener la información del hallazgo asignado.
            FindingAssigned findingAssigned = findingAssignedService.getFindingInfoByFinding(pFindingAssignedId);

            // Retorna una respuesta exitosa con el hallazgo asignado encontrado.
            Response<FindingAssigned> response = Response.<FindingAssigned>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(findingAssigned)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el hallazgo asignado, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<FindingAssigned> response = Response.<FindingAssigned>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<FindingAssigned> response = Response.<FindingAssigned>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para crear un nuevo hallazgo asignado.
     *
     * @param pFindingId el ID del hallazgo al que se asignará.
     * @param pNewFindingAssiged el objeto {@link FindingAssigned} que contiene los detalles del hallazgo asignado a crear.
     * @return una  ResponseEntity con un Response que contiene el hallazgo asignado creado o un mensaje de error.
     */
    @PostMapping("/finding/{pFindingId}/user/{userId}")
    public ResponseEntity<Response<FindingAssigned>> createFinding(@PathVariable Long pFindingId, @PathVariable Long userId,@RequestBody FindingAssigned pNewFindingAssiged) {
        try {
            // Llama al servicio para crear el hallazgo asignado.
            FindingAssigned finding = findingAssignedService.createFindingAssigned(pFindingId, userId,pNewFindingAssiged);

            // Retorna una respuesta exitosa con el hallazgo asignado creado.
            Response<FindingAssigned> response = Response.<FindingAssigned>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(finding)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el hallazgo, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<FindingAssigned> response = Response.<FindingAssigned>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<FindingAssigned> response = Response.<FindingAssigned>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/userFinish/{pFindingAssigedId}")
    public ResponseEntity<Response<FindingAssigned>> userFinishFinding(@PathVariable Long pFindingAssigedId, @RequestBody FindingAssigned pNewFinding) {
        try {
            // Llama al servicio para terminar el hallazgo asignado por parte del usuario.
            FindingAssigned finding = findingAssignedService.userFinishFinding(pFindingAssigedId, pNewFinding);

            // Retorna una respuesta exitosa con el hallazgo asignado creado.
            Response<FindingAssigned> response = Response.<FindingAssigned>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(finding)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el hallazgo, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<FindingAssigned> response = Response.<FindingAssigned>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<FindingAssigned> response = Response.<FindingAssigned>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/adminFinish/{pFindingAssigedId}")
    public ResponseEntity<Response<FindingAssigned>> adminFinishFinding(@PathVariable Long pFindingAssigedId) {
        try {
            // Llama al servicio para terminar el hallazgo asignado por parte del usuario.
            FindingAssigned finding = findingAssignedService.adminFinishFinding(pFindingAssigedId);

            // Retorna una respuesta exitosa con el hallazgo asignado creado.
            Response<FindingAssigned> response = Response.<FindingAssigned>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(finding)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el hallazgo, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<FindingAssigned> response = Response.<FindingAssigned>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<FindingAssigned> response = Response.<FindingAssigned>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
