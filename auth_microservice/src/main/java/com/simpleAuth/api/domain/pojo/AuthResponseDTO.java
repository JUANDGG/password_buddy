package com.simpleAuth.api.domain.pojo;

import org.springframework.http.HttpStatus;

public record AuthResponseDTO(String token, HttpStatus httpStatus) {
}
