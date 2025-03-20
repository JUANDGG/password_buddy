package com.managerPasswords.api.persistence.services;

import com.managerPasswords.api.domain.entities.PasswordManagementEntity;
import com.managerPasswords.api.persistence.repositories.PasswordManagementRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordManagementService {
    private final PasswordManagementRepository passwordManagementRepository;

    public  PasswordManagementService (PasswordManagementRepository passwordManagementRepository){
        this.passwordManagementRepository =passwordManagementRepository ;
    }



    public  Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        (authentication.getPrincipal())



    }


    public List<PasswordManagementEntity> getAllCredentialsForIdUser() {

    }


}
