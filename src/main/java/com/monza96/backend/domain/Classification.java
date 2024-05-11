package com.monza96.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_classification")
public class Classification implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    //region Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToOne
    private Project project;
    @ManyToMany(mappedBy = "classifications")
    @Setter(AccessLevel.NONE)
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "classification")
    @Setter(AccessLevel.NONE)
    private Set<Objective> objectives = new HashSet<>();
    //endregion

    public Classification(Long id, String title, Project project) {
        this.id = id;
        this.title = title;
        this.project = project;
    }

    //region Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classification that = (Classification) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(project);
        return result;
    }
    //endregion
}
