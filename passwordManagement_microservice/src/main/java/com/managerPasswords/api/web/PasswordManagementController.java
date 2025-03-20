package com.managerPasswords.api.web;


import com.managerPasswords.api.persistence.services.PasswordManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/my-passwords")
public class PasswordManagementController {

    private final  PasswordManagementService passwordManagementService ;

    public PasswordManagementController(PasswordManagementService passwordManagementService){
        this.passwordManagementService = passwordManagementService ;
    }


    @GetMapping
    public ResponseEntity<?> getAll(){
        return  ResponseEntity.ok(null);
    }

}
