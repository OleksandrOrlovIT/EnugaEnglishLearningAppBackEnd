package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.BaseEntity;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class TranslationPair extends BaseEntity {

    @ManyToOne
    private EnglishWord englishWord;

    @ManyToOne
    private UkrainianWord ukrainianWord;

    @Builder
    public TranslationPair(Long id, EnglishWord englishWord, UkrainianWord ukrainianWord) {
        super(id);
        this.englishWord = englishWord;
        this.ukrainianWord = ukrainianWord;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TranslationPair that = (TranslationPair) o;
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
                "ukrainianWord = " + getUkrainianWord() + ", " +
                "englishWord = " + getEnglishWord() + ")";
    }
}
