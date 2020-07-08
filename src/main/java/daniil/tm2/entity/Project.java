package daniil.tm2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Denis Volnenko
 */
@Entity
@Table(name = "TMNG_PROJECT")
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Project implements Serializable {

    @Id
    @Column(name = "PROJECT_NAME")
    private String name = "";

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "DOMAIN_ID")
    private Domain domain;

    @Column(name = "DATE_BEGIN")
    private LocalDate dateBegin;

    @Column(name = "DATE_END")
    private LocalDate dateEnd;

    @Column(name = "DATE_CREATED")
    private LocalDate created = LocalDate.now();

    //@JacksonXmlElementWrapper(localName = "tasks")
    //@JacksonXmlProperty(localName = "task")
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Task> tasks;
    
}
