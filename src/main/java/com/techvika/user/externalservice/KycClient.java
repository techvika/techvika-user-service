package com.techvika.user.externalservice;

import com.techvika.user.dto.KycResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "TECHVIKA-KYC-SERVICE")
public interface KycClient {

    @GetMapping("/api/kyc/{id}")
    public ResponseEntity<KycResponse> getUserById(
            @Parameter(description = "KYC ID") @PathVariable Long id);

}
