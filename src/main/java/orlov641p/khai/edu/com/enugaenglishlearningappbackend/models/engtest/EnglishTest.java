package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.BaseEntity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.engtest.TestAttempt;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class EnglishTest extends BaseEntity {

    private String testName;

    @OneToMany(mappedBy = "englishTest", cascade = CascadeType.REMOVE)
    private List<Question> questions;

    @OneToMany(mappedBy = "englishTest", cascade = CascadeType.REMOVE)
    private List<TestAttempt> testAttempts;

    @Builder
    public EnglishTest(Long id, String testName, List<Question> questions, List<TestAttempt> testAttempts) {
        super(id);
        this.testName = testName;
        this.questions = questions;
        this.testAttempts = testAttempts;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) return false;

        EnglishTest that = (EnglishTest) object;

        return Objects.equals(testName, that.testName);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (testName != null ? testName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EnglishTest{" +
                "id=" + getId() +
                ", testName='" + testName + '\'' +
                '}';
    }
}