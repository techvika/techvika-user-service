package com.techvika.user.client;

import com.techvika.user.dto.KycRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class KycClient {
    private static final Logger log = LoggerFactory.getLogger(KycClient.class);
    private static final String KYC_URL = "http://localhost:8081/api/kyc";

    public static void sendKycRequest(KycRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<KycRequest> entity = new HttpEntity<>(request, headers);
            restTemplate.postForEntity(KYC_URL, entity, Void.class);
        } catch (RestClientException e) {
            // Log and swallow exception to not fail user creation
            log.warn("Failed to call KYC service for user {}: {}", request.getUserId(), e.getMessage());
        }
    }
}