package com.example.SeguimientoHallazgo.Domain;

import com.example.SeguimientoHallazgo.Domain.Finding.Finding;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classification", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Classification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;


    @OneToMany(mappedBy = "classification", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Finding> finding;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    @JsonIgnore
    private Center center;
}
