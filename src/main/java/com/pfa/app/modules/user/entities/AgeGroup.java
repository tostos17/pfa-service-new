package com.pfa.app.modules.user.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "age_groups")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AgeGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name; // e.g., "U10", "U13", "U15"

    @Column(name = "min_age", nullable = false)
    private Integer minAge;

    @Column(name = "max_age", nullable = false)
    private Integer maxAge;

    @ManyToMany(mappedBy = "ageGroups")
    private Set<PlayerProfile> players = new HashSet<>();
}