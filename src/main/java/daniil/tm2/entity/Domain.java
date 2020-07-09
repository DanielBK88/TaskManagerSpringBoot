package daniil.tm2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Denis Volnenko
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "TMNG_DOMAIN")
public final class Domain implements Serializable {

    @Id
    @Column(name = "NAME")
    private String name;

    @JacksonXmlElementWrapper(localName = "projects")
    @JacksonXmlProperty(localName = "project")
    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Project> projects = new ArrayList<>();

    public Domain(String name) {
        this.name = name;
    }

    /**
     * Get all tasks of all projects on this domain
     **/
    @JsonIgnore
    public List<Task> getTasks() {
        return projects.stream()
                .flatMap(project -> project.getTasks().stream())
                .collect(Collectors.toList());
    }

}
