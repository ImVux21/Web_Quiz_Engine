package engine.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Quiz {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String text;

    @NotNull
    @Size(min = 2)
    @ElementCollection
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    private List<Integer> answer;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User createdBy;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Completion> completions;

    public boolean isCorrectAnswer(List<Integer> userAnswer) {
        if (answer == null) {
            answer = new ArrayList<>();
        }
        if (userAnswer == null) {
            userAnswer = new ArrayList<>();
        }

        userAnswer.sort(Integer::compareTo);
        answer.sort(Integer::compareTo);

        return userAnswer.equals(answer);
    }

    public boolean isCreatedBy(String authenticatedEmail) {
        return this.createdBy.getEmail().equals(authenticatedEmail);
    }
}