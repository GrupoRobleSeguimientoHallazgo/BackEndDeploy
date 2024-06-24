package com.example.SeguimientoHallazgo.Domain;

import com.example.SeguimientoHallazgo.Domain.Finding.FindingAssigned;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "material", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String quantity;

    @ManyToOne
    @JoinColumn(name = "finding_assigned_id", nullable = false)
    @JsonIgnore
    private FindingAssigned findingAssigned;
}