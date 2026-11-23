package com.techvika.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleSeqGenerator")
    @SequenceGenerator(name = "roleSeqGenerator", sequenceName = "roleSeq", allocationSize = 1)
    @JsonIgnore
    private Long id;

    private String name; // ADMIN, USER etc.,

    @Override
    public @Nullable String getAuthority() {
        return name;
    }
}
