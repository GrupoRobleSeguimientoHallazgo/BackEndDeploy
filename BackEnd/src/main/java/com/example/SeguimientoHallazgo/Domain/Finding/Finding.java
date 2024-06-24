package com.example.SeguimientoHallazgo.Domain.Finding;


import com.example.SeguimientoHallazgo.Common.Priority;
import com.example.SeguimientoHallazgo.Common.Status;
import com.example.SeguimientoHallazgo.Domain.Center;
import com.example.SeguimientoHallazgo.Domain.Classification;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "finding", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Finding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    String description;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    Date dateCreate;

    @Column(columnDefinition = "TEXT")
    String imageBase64;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Priority priority;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Status status;

    @ManyToOne
    @JoinColumn(name = "center_id", nullable = false)
    private Center center;

    @OneToOne(mappedBy = "finding", cascade = CascadeType.ALL)
    @JsonBackReference
    private FindingAssigned findingAssigned;

    @ManyToOne
    @JoinColumn(name = "classification_id", nullable = false)
    private Classification classification;
}