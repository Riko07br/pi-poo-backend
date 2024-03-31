package com.monza96.backend.domain;

import com.monza96.backend.domain.enums.Authority;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_role")
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    //region Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Integer authority;
    private String title;
    private String description;

    @OneToMany(mappedBy = "role")
    private Set<ProjectUser> projectUsers;
    //endregion

    public Role(Long id, Authority authority, String title, String description) {
        this.id = id;
        this.authority = authority.getValue();
        this.title = title;
        this.description = description;
    }

    public Authority getAuthority() {
        return Authority.valueOf(authority);
    }

    public void setAuthority(Authority authority) {
        this.authority = authority.getValue();
    }

    //region Equals & Hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    //endregion
}
