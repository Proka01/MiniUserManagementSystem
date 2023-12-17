package rs.raf.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.UpdateRequestDTO;
import rs.raf.demo.model.User;
import rs.raf.demo.model.Permission;
import rs.raf.demo.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@Valid @RequestBody User user) {
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new Permission("can_create_users"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userService.create(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new Permission("can_read_users"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public User me() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userService.findByEmail(email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new Permission("can_delete_users"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<User> update(@Valid @RequestBody UpdateRequestDTO userDTO) {
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new Permission("can_update_users"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userService.update(userDTO));
    }
}
