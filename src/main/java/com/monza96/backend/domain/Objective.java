package com.monza96.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_objective")
public class Objective implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    //region Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private Task task;
    @ManyToOne
    private Classification classification;
    //endregion

    public Objective(Long id, String title, String description, Task task, Classification classification) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.task = task;
        this.classification = classification;
    }

    //region Equals & Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Objective objective = (Objective) o;
        return Objects.equals(id, objective.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    //endregion
}
