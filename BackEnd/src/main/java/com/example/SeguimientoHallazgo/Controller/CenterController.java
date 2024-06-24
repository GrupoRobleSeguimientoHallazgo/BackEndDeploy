package com.example.SeguimientoHallazgo.Controller;

import com.example.SeguimientoHallazgo.Domain.Center;
import com.example.SeguimientoHallazgo.Exceptions.DuplicateException;
import com.example.SeguimientoHallazgo.Service.Interface.CenterService;
import com.example.SeguimientoHallazgo.Util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/center")
@RequiredArgsConstructor
public class CenterController {

    private final CenterService centerService;

    /**
     * Maneja las solicitudes GET para obtener todos los centros.
     *
     * @return ResponseEntity con la lista de centros y el estado HTTP correspondiente.
     */
    @GetMapping("/")
    public ResponseEntity<Response<List<Center>>> getAllCenters() {
        try {
            // Obtiene una lista de todos los centros utilizando el servicio de centro.
            List<Center> centers = centerService.listAllCenter();
            // Construye la respuesta exitosa.
            Response<List<Center>> response = Response.<List<Center>>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(centers)
                    .build();
            // Retorna la respuesta exitosa con el código de estado HTTP 200 (OK).
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            // En caso de error, construye una respuesta de error.
            Response<List<Center>> response = Response.<List<Center>>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            // Retorna la respuesta de error con el código de estado HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Maneja las solicitudes GET para obtener un centro por su ID.
     *
     * @param id El ID del centro solicitado.
     * @return ResponseEntity con el centro solicitado y el estado HTTP correspondiente.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response<Center>> getCenterById(@PathVariable int id) {
        try {
            // Obtiene la información del centro por su ID utilizando el servicio de centro.
            Center center = centerService.getCenterInfo(id);
            // Construye la respuesta exitosa.
            Response<Center> response = Response.<Center>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(center)
                    .build();
            // Retorna la respuesta exitosa con el código de estado HTTP 200 (OK).
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            // En caso de error, construye una respuesta de error.
            Response<Center> response = Response.<Center>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            // Retorna la respuesta de error con el código de estado HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Maneja las solicitudes POST para crear un nuevo centro.
     *
     * @param pNewCenter El nuevo centro que se va a crear.
     * @return ResponseEntity con el centro creado y el estado HTTP correspondiente.
     */
    @PostMapping("/")
    public ResponseEntity<Response<Center>> createCenter(@RequestBody Center pNewCenter) {
        try {
            // Crea el centro utilizando el servicio de centro.
            Center createdCenter = centerService.createCenter(pNewCenter);
            // Construye la respuesta exitosa.
            Response<Center> response = Response.<Center>builder()
                    .status("success")
                    .message("Center created successfully")
                    .data(createdCenter)
                    .build();
            // Retorna la respuesta exitosa con el código de estado HTTP 201 (Created).
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicateException e) {
            // En caso de que se detecte un conflicto debido a un centro duplicado, construye una respuesta de conflicto.
            Response<Center> response = Response.<Center>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            // Retorna la respuesta de conflicto con el código de estado HTTP 409 (Conflict).
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            // En caso de un error inesperado, construye una respuesta de error genérico.
            Response<Center> response = Response.<Center>builder()
                    .status("error")
                    .message("An unexpected error occurred")
                    .data(null)
                    .build();
            // Retorna la respuesta de error con el código de estado HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Maneja las solicitudes PUT para actualizar un centro existente.
     *
     * @param id         El ID del centro que se va a actualizar.
     * @param pNewCenter El nuevo centro con la información actualizada.
     * @return ResponseEntity con el centro actualizado y el estado HTTP correspondiente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Response<Center>> updateCenter(@PathVariable int id, @RequestBody Center pNewCenter) {
        try {
            // Actualiza el centro utilizando el servicio de centro.
            Center updatedCenter = centerService.updateCenter(id, pNewCenter);
            // Construye la respuesta exitosa.
            Response<Center> response = Response.<Center>builder()
                    .status("success")
                    .message("Center updated successfully")
                    .data(updatedCenter)
                    .build();
            // Retorna la respuesta exitosa con el código de estado HTTP 200 (OK).
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // En caso de un error inesperado, construye una respuesta de error genérico.
            Response<Center> response = Response.<Center>builder()
                    .status("error")
                    .message("An unexpected error occurred")
                    .data(null)
                    .build();
            // Retorna la respuesta de error con el código de estado HTTP 500 (Internal Server Error).
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
