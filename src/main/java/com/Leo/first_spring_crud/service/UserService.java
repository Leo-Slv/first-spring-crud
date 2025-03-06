package com.Leo.first_spring_crud.service;

import com.Leo.first_spring_crud.controller.Dto.AccountResponseDto;
import com.Leo.first_spring_crud.controller.Dto.CreateAccountDto;
import com.Leo.first_spring_crud.controller.Dto.CreateUserDto;
import com.Leo.first_spring_crud.controller.Dto.UpdateUserDto;
import com.Leo.first_spring_crud.entity.Account;
import com.Leo.first_spring_crud.entity.BillingAddress;
import com.Leo.first_spring_crud.entity.User;
import com.Leo.first_spring_crud.repository.AccountRepository;
import com.Leo.first_spring_crud.repository.BillingAddressRepository;
import com.Leo.first_spring_crud.repository.UserRepository;
import com.sun.net.httpserver.HttpsServer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID createUser(CreateUserDto createUserDto){

        var Entity = new User(UUID.randomUUID(),
                createUserDto.name(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);
        var UserSaved = userRepository.save(Entity);

        return UserSaved.getId();
    }

    public Optional <User> getUserById(String id){

        return userRepository.findById(UUID.fromString(id));
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public void updateUserById (String id,
                                UpdateUserDto updateUserDto) {

        var userId = UUID.fromString(id);

        var userEntity = userRepository.findById(userId);

        if(userEntity.isPresent()) {

            var user = userEntity.get();

            if(updateUserDto.name() != null) {
                user.setName(updateUserDto.name());
            }

           if(updateUserDto.password() != null) {
               user.setPassword(updateUserDto.password());
           }
           userRepository.save(user);
        }
    }

    public void deleteUserById(String id){

        var userId = UUID.fromString(id);

        var userExists = userRepository.existsById(userId);

        if(userExists){
            userRepository.deleteById(userId);
        }
    }

    public void createAccount(String id, CreateAccountDto createAccountDto) {

        var user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //DTO -> ENTITY
        var account = new Account(
                user,
                null,
                UUID.randomUUID(),
                createAccountDto.description(),
                new ArrayList<>()
                );

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                account,
                createAccountDto.street(),
                createAccountDto.number()
        );


        billingAddressRepository.save(billingAddress);
    }

    public List<AccountResponseDto> listAccount(String id) {

        var user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts()
                .stream()
                .map(ac ->
                        new AccountResponseDto(ac.getAccountId().toString(), ac.getDescription()))
                .toList();


    }
}
