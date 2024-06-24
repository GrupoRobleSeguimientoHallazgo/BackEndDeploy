package com.example.SeguimientoHallazgo.Controller;

import com.example.SeguimientoHallazgo.Domain.Material;
import com.example.SeguimientoHallazgo.Exceptions.NotFoundException;
import com.example.SeguimientoHallazgo.Service.Interface.MaterialService;
import com.example.SeguimientoHallazgo.Util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping("/findingAssigned/{pFindingAssigned}")
    public ResponseEntity<Response<List<Material>>> getAllMaterialByFindingAssigned(@PathVariable Long pFindingAssigned) {
        try {
            // Llama al servicio para obtener la lista de hallazgos asignados del centro especificado.
            List<Material> materials = materialService.listAllMaterialByFindingAssigned(pFindingAssigned);

            // Retorna una respuesta exitosa con la lista de hallazgos asignados.
            Response<List<Material>> response = Response.<List<Material>>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(materials)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el usuario, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<List<Material>> response = Response.<List<Material>>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<List<Material>> response = Response.<List<Material>>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/findingAssigned/{pFindingAssigned}")
    public ResponseEntity<Response<Material>> saveMaterial(@PathVariable Long pFindingAssigned, @RequestBody Material pMaterial) {
        try {
            // Llama al servicio para obtener la lista de hallazgos asignados del centro especificado.
            Material material = materialService.saveMaterial(pFindingAssigned, pMaterial);

            // Retorna una respuesta exitosa con la lista de hallazgos asignados.
            Response<Material> response = Response.<Material>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(material)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el usuario, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<Material> response = Response.<Material>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<Material> response = Response.<Material>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{pMaterialId}")
    public ResponseEntity<Response<Material>> updateMaterial(@PathVariable Long pMaterialId, @RequestBody Material pMaterial) {
        try {
            // Llama al servicio para obtener la lista de hallazgos asignados del centro especificado.
            Material material = materialService.updateMaterial(pMaterialId, pMaterial);

            // Retorna una respuesta exitosa con la lista de hallazgos asignados.
            Response<Material> response = Response.<Material>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(material)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el usuario, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<Material> response = Response.<Material>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<Material> response = Response.<Material>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{pMaterialId}")
    public ResponseEntity<Response<Boolean>> deleteMaterial(@PathVariable Long pMaterialId) {
        try {
            // Llama al servicio para obtener la lista de hallazgos asignados del centro especificado.
            boolean material = materialService.deleteMaterial(pMaterialId);

            // Retorna una respuesta exitosa con la lista de hallazgos asignados.
            Response<Boolean> response = Response.<Boolean>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(material)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            // Si no se encuentra el usuario, retorna una respuesta con un estado HTTP 404 (Not Found).
            Response<Boolean> response = Response.<Boolean>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Si ocurre un error inesperado, retorna una respuesta con un estado HTTP 500 (Internal Server Error).
            Response<Boolean> response = Response.<Boolean>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
