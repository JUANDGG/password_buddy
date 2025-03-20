package com.managerPasswords.api.domain.entities;


import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "password_management")
@Getter @Setter @Builder
public class PasswordManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name = "id_user")
    private Long  idUser ;

    @Column(name = "name_site")
    private String nameSite;

    @Column(name = "password_site")
    private String passwordSite ;
}
