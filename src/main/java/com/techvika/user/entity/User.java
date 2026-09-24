package com.techvika.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGenerator")
    @SequenceGenerator(name = "userSeqGenerator", sequenceName = "userSeq", allocationSize = 1)
    @JsonIgnore
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String mobile;

    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "kyc_status")
    private KycStatus kycStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDate.now();
        }
        if (this.kycStatus == null) {
            this.kycStatus = KycStatus.PENDING;
        }
    }
}
