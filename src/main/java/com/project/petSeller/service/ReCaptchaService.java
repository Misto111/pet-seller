package com.project.petSeller.service;

import com.project.petSeller.model.dto.ReCaptchaResponseDTO;

import java.util.Optional;

public interface ReCaptchaService {
    Optional<ReCaptchaResponseDTO> verify(String token);
}
