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
import java.util.Optional;

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
        Optional<PasswordManagementEntity> searchByName = passwordManagementRepository.getSite(passwordManagamentDTO.nameSite(),idUserAuthenticate);

        if(searchByName.isPresent()){
            throw  new RuntimeException("no se pude crear otro sitio con el mismo nombre");
        }

        PasswordManagementEntity passwordManagementEntity = PasswordManagamentMapper.toModel(idUserAuthenticate, passwordManagamentDTO);
        passwordManagementRepository.save(passwordManagementEntity);
    }

    public void putPasswordManagement (PasswordManagamentDTO passwordManagamentDTO){
        Long idUserAuthenticate  = getCurrentUserId();

        Optional<PasswordManagementEntity> searchByName = passwordManagementRepository.getSite(passwordManagamentDTO.nameSite(),idUserAuthenticate);

        if(searchByName.isEmpty()){
            //handle this exeption
            throw  new RuntimeException("no se pude modificar algo que no existe");
        }

        PasswordManagementEntity model =searchByName.get();
        model.setIdUser(idUserAuthenticate);
        model.setNameSite(passwordManagamentDTO.nameSite());
        model.setPasswordSite(passwordManagamentDTO.passwordSite());

        passwordManagementRepository.save(model);
    }


    public void deletePasswordManagement (String nameSite){
        Long idUserAuthenticate  = getCurrentUserId();
        Optional<PasswordManagementEntity> searchByName = passwordManagementRepository.getSite(nameSite,idUserAuthenticate);
        if(searchByName.isEmpty()){
            throw  new RuntimeException("no se pude eliminar algo que no existe");
        }
        passwordManagementRepository.delete(searchByName.get());
    }

}
