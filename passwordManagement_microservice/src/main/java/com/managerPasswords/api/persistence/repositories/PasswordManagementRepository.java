package com.managerPasswords.api.persistence.repositories;

import com.managerPasswords.api.domain.entities.PasswordManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.expression.spel.ast.OpInc;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PasswordManagementRepository extends JpaRepository<PasswordManagementEntity,Long> {

    @Query("select p from PasswordManagementEntity p where p.idUser = ?1")
    List<PasswordManagementEntity> getALlCredentialsForIdUser (Long id_user);
}
