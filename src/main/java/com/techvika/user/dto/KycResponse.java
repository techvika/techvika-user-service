package com.techvika.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techvika.user.entity.KycStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycResponse {

    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("pan_number")
    private String panNumber;

    private KycStatus status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}