package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.BaseEntity;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CustomPair extends BaseEntity {

    @NotEmpty
    private String word;

    @NotEmpty
    private String translation;

    @ManyToOne
    @JoinColumn(name = "wordModule_id")
    private WordModule wordModule;

    @Builder
    public CustomPair(Long id, String word, String translation, WordModule wordModule) {
        super(id);
        this.word = word;
        this.translation = translation;
        this.wordModule = wordModule;
    }

    public CustomPair(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CustomPair that = (CustomPair) o;
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
                "wordModuleId = " + getWordModule().getId() + ", " +
                "translation = " + getTranslation() + ", " +
                "word = " + getWord() + ")";
    }
}
