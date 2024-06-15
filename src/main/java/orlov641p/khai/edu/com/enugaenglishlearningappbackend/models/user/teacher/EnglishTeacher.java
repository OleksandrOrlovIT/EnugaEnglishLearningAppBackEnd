package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.BaseEntity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "english_teachers")
public class EnglishTeacher extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<EnglishStudent> students;

    @Builder
    public EnglishTeacher(Long id, User user, List<EnglishStudent> students) {
        super(id);
        this.user = user;
        this.students = students;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        EnglishTeacher that = (EnglishTeacher) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        String userId = null;

        if(getUser() != null){
            userId = "userId = " + getUser().getId() + ")";
        }

        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                userId;
    }
}