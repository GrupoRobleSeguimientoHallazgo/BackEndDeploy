package com.example.SeguimientoHallazgo.Domain.Finding;

import com.example.SeguimientoHallazgo.Domain.Comment;
import com.example.SeguimientoHallazgo.Domain.Material;
import com.example.SeguimientoHallazgo.Domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "findingAssigned", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class FindingAssigned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String workOrder;

    Date dateEnd;
    boolean acceptFinish;

    @Column(columnDefinition = "TEXT")
    String imageBase64Finish;

    @OneToOne
    @JoinColumn(name = "finding_id", nullable = false)
    @JsonManagedReference
    private Finding finding;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "findingAssigned", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Material> materials;

    @OneToMany(mappedBy = "findingAssigned", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
