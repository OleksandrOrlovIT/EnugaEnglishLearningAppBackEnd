package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Question extends BaseEntity{

    @Column(length = 1000)
    private String questionText;

    private String answer;

    @Builder
    public Question(Long id, String questionText, String answer) {
        super(id);
        this.questionText = questionText;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionText='" + questionText + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Question question = (Question) object;

        if (!Objects.equals(questionText, question.questionText))
            return false;
        return Objects.equals(answer, question.answer);
    }

    @Override
    public int hashCode() {
        int result = questionText != null ? questionText.hashCode() : 0;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }
}