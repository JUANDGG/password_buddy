package com.managerPasswords.api.persistence.services;

import com.managerPasswords.api.domain.entities.PasswordManagementEntity;
import com.managerPasswords.api.domain.pojo.PasswordManagamentDTO;
import com.managerPasswords.api.domain.pojo.PasswordManagamentMapper;
import com.managerPasswords.api.domain.util.CustomUserDetails;
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



    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            return customUserDetails.getId();
        }
        return null;
    }


    public List<PasswordManagementEntity> getAllCredentialsForIdUser() {
        Long idUserAuthenticate  = getCurrentUserId();
         return passwordManagementRepository.getALlCredentialsForIdUser(idUserAuthenticate);
    }


    public void savePasswordManagement (PasswordManagamentDTO passwordManagamentDTO){
        Long idUserAuthenticate  = getCurrentUserId();
        PasswordManagementEntity passwordManagementEntity = PasswordManagamentMapper.toModel(idUserAuthenticate, passwordManagamentDTO);
        passwordManagementRepository.save(passwordManagementEntity);
    }






}
