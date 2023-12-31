package rs.raf.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.User;
import rs.raf.demo.requests.LoginRequest;
import rs.raf.demo.responses.LoginResponse;
import rs.raf.demo.services.UserService;
import rs.raf.demo.utils.JwtUtil;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (Exception   e){
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }

        User u = userService.findByEmail(loginRequest.getEmail());
        boolean createUsersPerm = u.getPermissions().contains("can_create_users");
        boolean readUsersPerm = u.getPermissions().contains("can_read_users");
        boolean updateUsersPerm = u.getPermissions().contains("can_update_users");
        boolean deleteUsersPerm = u.getPermissions().contains("can_delete_users");

        System.out.println(loginRequest.getEmail());
        System.out.println(createUsersPerm);
        System.out.println(readUsersPerm);
        System.out.println(updateUsersPerm);
        System.out.println(deleteUsersPerm);

        System.out.println("USOOOOOOOOOOOOOO ALEKSA");

        return ResponseEntity.ok(
                new LoginResponse(
                        jwtUtil.generateToken(loginRequest.getEmail(), userService.findByEmail(loginRequest.getEmail()).getPermissions()),
                        readUsersPerm,
                        createUsersPerm,
                        updateUsersPerm,
                        deleteUsersPerm
                )
        );
    }

}
