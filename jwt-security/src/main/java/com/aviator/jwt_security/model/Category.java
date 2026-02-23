package com.aviator.jwt_security.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID Id;

    @Column(unique = true, nullable = false)
    public String name;

    @Column(unique = true, nullable = false)
    public String slug;

    public String description;

    @Column(name = "created_at")
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    public LocalDateTime createdAt;
}
