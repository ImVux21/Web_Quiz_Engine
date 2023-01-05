package engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Completion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "completion_id", nullable = false)
    private Long id;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User completedBy;

    @ManyToOne
    @JsonUnwrapped
    @JsonIgnoreProperties({"title", "text", "options"})
    private Quiz quiz;

    private LocalDateTime completedAt = LocalDateTime.now();

    public Completion(User completedBy, Quiz quiz) {
        this.completedBy = completedBy;
        this.quiz = quiz;
    }
}