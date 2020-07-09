package daniil.tm2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Denis Volnenko
 */
@Entity
@Table(name = "TMNG_TASK")
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Task implements Serializable {

    @Id
    @Column(name = "TASK_NAME")
    private String name = "";

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @Column(name = "DATE_BEGIN")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateBegin;

    @Column(name = "DATE_END")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEnd;

    public void test() {
        System.out.println("HELLO");
    }

    public Task(String name, Project project) {
        this.name = name;
        this.project = project;
    }

}
