package com.managerPasswords.api.domain.pojo;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class PasswordManagamentDTO {


        @NotBlank(message = "El nombre del sitio no puede estar vacío")
        @Size(min = 3, max = 50, message = "El nombre del sitio debe tener entre 3 y 50 caracteres")
       private String nameSite ;


        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        private String passwordSite ;

    @JsonCreator
    public PasswordManagamentDTO(
            @JsonProperty("nameSite") String nameSite,
            @JsonProperty("passwordSite") String passwordSite
    ) {
        this.nameSite = nameSite;
        this.passwordSite = passwordSite;
    }
}
