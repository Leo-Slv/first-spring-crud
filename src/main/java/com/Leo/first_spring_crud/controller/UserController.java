package com.Leo.first_spring_crud.controller;

import com.Leo.first_spring_crud.controller.Dto.AccountResponseDto;
import com.Leo.first_spring_crud.controller.Dto.CreateAccountDto;
import com.Leo.first_spring_crud.controller.Dto.CreateUserDto;
import com.Leo.first_spring_crud.controller.Dto.UpdateUserDto;
import com.Leo.first_spring_crud.entity.User;
import com.Leo.first_spring_crud.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {


    private UserService userService;

    public UserController (UserService userService){
        this.userService =  userService;
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        var userId = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/user/" + userId.toString())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        var user = userService.getUserById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        var users = userService.listUsers();

        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserById (@PathVariable("id") String id,
                                                @RequestBody UpdateUserDto updateUserDto){

        userService.updateUserById(id, updateUserDto);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") String id) {

        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();

    }

    @PostMapping("/{id}/accounts")
    public ResponseEntity<Void> createAccount(@PathVariable("id") String id,
                                           @RequestBody CreateAccountDto createAccountDto) {

        userService.createAccount(id, createAccountDto);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<List<AccountResponseDto>> listAccounts(@PathVariable("id") String id) {

        var accounts = userService.listAccount(id);

        return ResponseEntity.ok(accounts);

    }
}

