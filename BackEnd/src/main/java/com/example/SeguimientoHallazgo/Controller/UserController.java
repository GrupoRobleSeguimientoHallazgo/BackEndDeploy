package com.example.SeguimientoHallazgo.Controller;

import com.example.SeguimientoHallazgo.Domain.DTO.Projections.IUserDTO;
import com.example.SeguimientoHallazgo.Domain.User;
import com.example.SeguimientoHallazgo.Exceptions.NotFoundException;
import com.example.SeguimientoHallazgo.Service.Interface.UserService;
import com.example.SeguimientoHallazgo.Util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping(value = "test")
    public String test(){
        return "Test from private enpoint";
    }

    @GetMapping("/center/{pCenterId}")
    public ResponseEntity<Response<List<IUserDTO>>> getAllUsersByCenter(@PathVariable int pCenterId) {
        try {
            List<IUserDTO> users = userService.listAllUsersByCenter(pCenterId);

            Response<List<IUserDTO>> response = Response.<List<IUserDTO>>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(users)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            Response<List<IUserDTO>> response = Response.<List<IUserDTO>>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Response<List<IUserDTO>> response = Response.<List<IUserDTO>>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{pUserId}")
    public ResponseEntity<Response<IUserDTO>> getAllUser(@PathVariable Long pUserId) {
        try {
            IUserDTO user = userService.getUserInfo(pUserId);

            Response<IUserDTO> response = Response.<IUserDTO>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(user)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            Response<IUserDTO> response = Response.<IUserDTO>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Response<IUserDTO> response = Response.<IUserDTO>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/center/{pCenterId}")
    public ResponseEntity<Response<IUserDTO>> createUser(@PathVariable int pCenterId, @RequestBody User pUser) {
        try {
            IUserDTO user = userService.registerUser(pCenterId, pUser);

            Response<IUserDTO> response = Response.<IUserDTO>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(user)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {

            Response<IUserDTO> response = Response.<IUserDTO>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Response<IUserDTO> response = Response.<IUserDTO>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{pUserId}")
    public ResponseEntity<Response<User>> updateUser(@PathVariable Long pUserId, @RequestBody User pUser) {
        try {
            User user = userService.updateUser(pUserId, pUser);

            Response<User> response = Response.<User>builder()
                    .status("success")
                    .message("Request was successful")
                    .data(user)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {

            Response<User> response = Response.<User>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Response<User> response = Response.<User>builder()
                    .status("error")
                    .message(e.getMessage())
                    .data(null)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
