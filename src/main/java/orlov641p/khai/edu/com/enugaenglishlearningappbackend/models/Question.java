package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "english_test_id")
    private EnglishTest englishTest;

    @Builder
    public Question(Long id, String questionText, String answer, EnglishTest englishTest) {
        super(id);
        this.questionText = questionText;
        this.answer = answer;
        this.englishTest = englishTest;
    }

    public Question(String questionText, String answer, EnglishTest englishTest) {
        this.questionText = questionText;
        this.answer = answer;
        this.englishTest = englishTest;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Question question = (Question) object;

        if (!Objects.equals(questionText, question.questionText))
            return false;
        if (!Objects.equals(answer, question.answer)) return false;
        return Objects.equals(englishTest, question.englishTest);
    }

    @Override
    public int hashCode() {
        int result = questionText != null ? questionText.hashCode() : 0;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (englishTest != null ? englishTest.hashCode() : 0);
        return result;
    }
}