package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.BaseEntity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class WordModuleAttempt extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wordModule_id", nullable = false)
    private WordModule wordModule;

    @NotNull
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime attemptDate;

    @NotNull
    @Min(0)
    private Integer rightAnswers;

    @NotNull
    @ElementCollection
    @CollectionTable(name = "word_module_attempt_wrong_answers",
            joinColumns = @JoinColumn(name = "word_module_attempt_id"))
    @MapKeyColumn(name = "custom_pair_id")
    @Column(name = "wrong_answer")
    private Map<Long, String> wrongAnswers;

    @NotNull
    @DecimalMin(value = "0.00")
    private Double successPercentage;

    @Builder
    public WordModuleAttempt(Long id, User user, WordModule wordModule, LocalDateTime attemptDate, Integer rightAnswers,
                             Map<Long, String> wrongAnswers, Double successPercentage) {
        super(id);
        this.user = user;
        this.wordModule = wordModule;
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
        WordModuleAttempt that = (WordModuleAttempt) o;
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
                "successPercentage = " + getSuccessPercentage() + ", " +
                "wrongAnswers = " + getWrongAnswers() + ", " +
                "rightAnswers = " + getRightAnswers() + ", " +
                "attemptDate = " + getAttemptDate() + ")";
    }
}
