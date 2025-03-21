package com.managerPasswords.api.domain.pojo;

import com.managerPasswords.api.domain.entities.PasswordManagementEntity;

public abstract class PasswordManagamentMapper {

    public static PasswordManagamentDTO  toDto (PasswordManagementEntity passwordManagementEntity){
        return PasswordManagamentDTO.builder()
                .nameSite(passwordManagementEntity.getNameSite())
                .passwordSite(passwordManagementEntity.getPasswordSite())
                .build() ;
    }

    public static  PasswordManagementEntity toModel (Long id_user ,PasswordManagamentDTO passwordManagamentDTO){
            return PasswordManagementEntity .builder()
                    .nameSite(passwordManagamentDTO.getNameSite())
                    .passwordSite(passwordManagamentDTO.getPasswordSite())
                    .idUser(id_user)
                    .build() ;
    }
}
