package com.datatransferobjectmanual.controller;

import com.datatransferobjectmanual.entity.User;
import com.datatransferobjectmanual.mapper.UserDto;
import com.datatransferobjectmanual.mapper.UserMapper;
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
    private ResponseEntity<List<UserDto>> getUsersList() {
        return new ResponseEntity<>(UserMapper.toUsersDtos(iUserService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    private ResponseEntity<UserDto> getUserByUsername(@PathVariable(name = "username") String username) {
        return new ResponseEntity<>(UserMapper.toUserDto(iUserService.findByUsername(username)), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    private ResponseEntity<UserDto> getUserByEmail(@PathVariable(name = "email") String email) {
        return new ResponseEntity<>(UserMapper.toUserDto(iUserService.findByEmail(email)), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<UserDto> saveUser(@RequestBody @Valid User user) {
        User savedUser = iUserService.save(user);
        UserDto userDto = UserMapper.toUserDto(savedUser);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/username/{username}")
                .buildAndExpand(userDto.getUsername())
                .toUri();
        return ResponseEntity.created(location).body(userDto);
    }

    @PutMapping("/{id}")
    private ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") Long id, @RequestBody @Valid User user) {
        User savedUser = iUserService.update(id, user);
        UserDto userDto = UserMapper.toUserDto(savedUser);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/username/{username}")
                .buildAndExpand(userDto.getUsername())
                .toUri();
        return ResponseEntity.created(location).body(userDto);
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
