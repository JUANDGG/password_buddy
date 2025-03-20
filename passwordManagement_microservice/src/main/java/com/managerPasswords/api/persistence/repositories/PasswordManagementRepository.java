package com.managerPasswords.api.persistence.repositories;

import com.managerPasswords.api.domain.entities.PasswordManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.expression.spel.ast.OpInc;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordManagementRepository extends JpaRepository<PasswordManagementEntity,Long> {

    @Query("select * from PasswordManagementEntity where idUser = ?")
    List<PasswordManagementEntity> getALlCredentialsForIdUser (Long id_user);
}
