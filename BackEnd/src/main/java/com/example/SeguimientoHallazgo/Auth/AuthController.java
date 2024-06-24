package com.example.SeguimientoHallazgo.Auth;

import com.example.SeguimientoHallazgo.Domain.User;
import com.example.SeguimientoHallazgo.Util.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) throws JsonProcessingException {

        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    /*@PutMapping(value = "changePassword")
    public ResponseEntity<Response<User>> changePassword(@RequestBody RegisterRequest request){

        return ResponseEntity.ok(authService.register(request));
    }*/

}
