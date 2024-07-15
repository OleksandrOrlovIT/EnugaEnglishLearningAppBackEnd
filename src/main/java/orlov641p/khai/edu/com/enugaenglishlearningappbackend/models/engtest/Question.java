package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.BaseEntity;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Question extends BaseEntity {

    @NotEmpty
    @Column(length = 1000)
    private String questionText;

    @NotEmpty
    private String answer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "english_test_id", nullable = false)
    private EnglishTest englishTest;

    @Builder
    public Question(Long id, String questionText, String answer, EnglishTest englishTest) {
        super(id);
        this.questionText = questionText;
        this.answer = answer;
        this.englishTest = englishTest;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) return false;

        Question question = (Question) object;

        if (!Objects.equals(questionText, question.questionText)) {
            return false;
        }

        return Objects.equals(answer, question.answer);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (questionText != null ? questionText.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + getId() +
                ", questionText='" + questionText + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}