package com.datatransferobjectmanual.controller;

import com.datatransferobjectmanual.entity.User;
import com.datatransferobjectmanual.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Created 02/10/2022 - 11:02
 * @Package com.datatransferobjectmanual.controller
 * @Project Data-Transfer-Object-Manual
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping
    private ResponseEntity<List<User>> getUsersList() {
        return new ResponseEntity<>(iUserService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    private ResponseEntity<User> getUserByUsername(@PathVariable(name = "username") String username) {
        return new ResponseEntity<>(iUserService.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    private ResponseEntity<User> getUserByEmail(@PathVariable(name = "email") String email) {
        return new ResponseEntity<>(iUserService.findByEmail(email), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
        User savedUser = iUserService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/username/{username}")
                .buildAndExpand(savedUser.getUsername())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }

    @PutMapping("/{id}")
    private ResponseEntity<User> updateUser(@PathVariable(name = "id") Long id, @RequestBody @Valid User user) {
        User savedUser = iUserService.update(id,
                user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/username/{username}")
                .buildAndExpand(savedUser.getUsername())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        iUserService.delete(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());
        errors.forEach((error) -> {
            String key = ((FieldError) error).getField();
            String val = error.getDefaultMessage();
            map.put(key, val);
        });
        return ResponseEntity.badRequest().body(map);
    }
}
