package com.example.SeguimientoHallazgo.Domain;

import com.example.SeguimientoHallazgo.Domain.Finding.FindingAssigned;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(columnDefinition = "TEXT")
    String commentInfo;

    Date dateCreated;
    String userName;

    @ManyToOne
    @JoinColumn(name = "finding_assigned_id", nullable = false)
    @JsonIgnore
    private FindingAssigned findingAssigned;

}
