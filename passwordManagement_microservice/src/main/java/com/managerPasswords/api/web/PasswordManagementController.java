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


    @PutMapping("/{nameSite}")
    public ResponseEntity<Void> putPasswordSite(@PathVariable String nameSite  ,@RequestBody PasswordManagamentDTO passwordManagamentDTO){
        passwordManagementService.putPasswordManagement(passwordManagamentDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{nameSite}")
    public ResponseEntity<Void> deletePasswordSite(@PathVariable String nameSite ){
        passwordManagementService.deletePasswordManagement(nameSite);
        return ResponseEntity.noContent().build();
    }


}
