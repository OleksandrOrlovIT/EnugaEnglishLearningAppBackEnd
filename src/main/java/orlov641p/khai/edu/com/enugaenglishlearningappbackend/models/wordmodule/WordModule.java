package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.BaseEntity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.enums.Visibility;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WordModule extends BaseEntity {

    @NotEmpty
    private String moduleName;

    @Enumerated(EnumType.ORDINAL)
    private Visibility visibility;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wordModule", cascade = CascadeType.REMOVE)
    private List<CustomPair> customPairs;

    @OneToMany(mappedBy = "wordModule", cascade = CascadeType.REMOVE)
    private List<WordModuleAttempt> wordModuleAttempts;

    @Builder
    public WordModule(Long id, String moduleName, Visibility visibility, User user, List<CustomPair> customPairs,
                      List<WordModuleAttempt> wordModuleAttempts) {
        super(id);
        this.moduleName = moduleName;
        this.visibility = visibility;
        this.user = user;
        this.customPairs = customPairs;
        this.wordModuleAttempts = wordModuleAttempts;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        WordModule that = (WordModule) o;
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
                "user = " + getUser() + ", " +
                "visibility = " + getVisibility() + ", " +
                "moduleName = " + getModuleName() + ")";
    }
}
