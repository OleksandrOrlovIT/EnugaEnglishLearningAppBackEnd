package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class EnglishTest extends BaseEntity{

    private String testName;

    @OneToMany(mappedBy = "englishTest", cascade = CascadeType.ALL)
    private List<Question> questions;

    @Builder
    public EnglishTest(Long id, String testName, List<Question> questions) {
        super(id);
        this.testName = testName;
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "EnglishTest{" +
                "testName='" + testName + '\'' +
                ", questions=" + questions +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        EnglishTest englishTest = (EnglishTest) object;

        if (!Objects.equals(testName, englishTest.testName)) return false;
        return Objects.equals(questions, englishTest.questions);
    }

    @Override
    public int hashCode() {
        int result = testName != null ? testName.hashCode() : 0;
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        return result;
    }
}