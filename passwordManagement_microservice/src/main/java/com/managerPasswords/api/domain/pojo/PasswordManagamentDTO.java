package com.managerPasswords.api.domain.pojo;


import lombok.Builder;

@Builder
public record PasswordManagamentDTO (String nameSite ,String passwordSite){}
