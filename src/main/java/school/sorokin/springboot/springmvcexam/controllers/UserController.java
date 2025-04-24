package school.sorokin.springboot.springmvcexam.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sorokin.springboot.springmvcexam.models.UserDto;
import school.sorokin.springboot.springmvcexam.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserDto userDto){
        UserDto createdUser = userService.creteUser(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable("id") Long id){
        UserDto foundedUser = userService.getUser(id);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(foundedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@RequestBody @Valid UserDto userDto, @PathVariable("id") Long id){
        UserDto updatedUser = userService.updateUser(userDto, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
       userService.removeUser(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
