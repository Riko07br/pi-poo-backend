package com.monza96.backend.domain;

import jakarta.persistence.Column;
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
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_task")
public class Task implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    //region Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Instant dueTime;

    @ManyToOne
    private Project project;
    @ManyToMany
    @Setter(AccessLevel.NONE)
    private Set<ProjectUser> projectUsers = new HashSet<>();
    @OneToMany(mappedBy = "task")
    @Setter(AccessLevel.NONE)
    private Set<Objective> objectives = new HashSet<>();
    //endregion

    public Task(Long id, String title, String description, Instant dueTime, Project project) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueTime = dueTime;
        this.project = project;
    }

    //region Equals & Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(title);
        return result;
    }
    //endregion
}
