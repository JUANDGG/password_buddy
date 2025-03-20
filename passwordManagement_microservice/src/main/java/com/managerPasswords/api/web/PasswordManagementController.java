package com.managerPasswords.api.web;


import com.managerPasswords.api.domain.entities.PasswordManagementEntity;
import com.managerPasswords.api.domain.pojo.PasswordManagamentDTO;
import com.managerPasswords.api.persistence.services.PasswordManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/my-passwords")
public class PasswordManagementController {

    private final  PasswordManagementService passwordManagementService ;

    public PasswordManagementController(PasswordManagementService passwordManagementService){
        this.passwordManagementService = passwordManagementService ;
    }


    @GetMapping
    public ResponseEntity<List<PasswordManagementEntity>> getAll(){
        return  ResponseEntity.ok(passwordManagementService.getAllCredentialsForIdUser());
    }

    @PostMapping
    public ResponseEntity<Void> savePasswordSite(@RequestBody PasswordManagamentDTO passwordManagamentDTO){
        passwordManagementService.savePasswordManagement(passwordManagamentDTO);
        return ResponseEntity.noContent().build();
    }


}
