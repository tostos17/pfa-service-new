package com.pfa.app.modules.user.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "player_profiles")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PlayerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "jersey_number")
    private Integer jerseyNumber;

    private String position;

    @Column(name = "dominant_foot")
    private String dominantFoot;

    @Column(name = "height_cm")
    private Double heightCm;

    @Column(name = "weight_kg")
    private Double weightKg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private User parent;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JoinTable(
            name = "player_groups",
            joinColumns = @JoinColumn(name = "player_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "age_group_id")
    )
    private Set<AgeGroup> ageGroups = new HashSet<>();
}