package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.engtest;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.BaseEntity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class TestAttempt extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private EnglishTest englishTest;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime attemptDate;

    private Integer rightAnswers;

    @ElementCollection
    @CollectionTable(name = "test_attempt_wrong_answers",
                     joinColumns = @JoinColumn(name = "test_attempt_id"))
    @MapKeyColumn(name = "question_id")
    @Column(name = "wrong_answer")
    private Map<Long, String> wrongAnswers;

    private Double successPercentage;

    @Builder
    public TestAttempt(Long id, User user, EnglishTest englishTest, LocalDateTime attemptDate, int rightAnswers,
                       Map<Long, String> wrongAnswers, double successPercentage) {
        super(id);
        this.user = user;
        this.englishTest = englishTest;
        this.attemptDate = attemptDate;
        this.rightAnswers = rightAnswers;
        this.wrongAnswers = wrongAnswers;
        this.successPercentage = successPercentage;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TestAttempt that = (TestAttempt) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "attemptDate = " + getAttemptDate() + ", " +
                "rightAnswers = " + getRightAnswers() + ", " +
                "wrongAnswers = " + getWrongAnswers() + ", " +
                "successPercentage = " + getSuccessPercentage() + ")";
    }
}