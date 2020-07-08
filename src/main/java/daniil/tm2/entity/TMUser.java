package daniil.tm2.entity;

import daniil.tm2.entity.role.Permission;
import daniil.tm2.entity.role.RolePermission;
import daniil.tm2.entity.role.UserRole;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TMNG_USER")
public class TMUser {
    
    @Id
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "PASSWORD_HASH")
    private String passwordHash;

    /**
     * The user roles, defining the user's permissions
     **/
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "TMNG_USER_ROLE",
            joinColumns = { @JoinColumn(name = "ROLE") },
            inverseJoinColumns = { @JoinColumn(name = "PERMISSION") }
    )
    private List<UserRole> roles;

    /**
     * The date when the user signed up to the system
     **/
    @Column(name = "DATE_JOINED")
    private LocalDate dateJoined;

    /**
     * Check, whether the user has the specified permission according to his user role
     **/
    public boolean hasPermission(Permission permisson) {
        return roles.stream()
                .flatMap(userRole -> userRole.getPermissons().stream())
                .map(RolePermission::getPermission)
                .anyMatch(p -> p.equals(permisson));
    }
}
