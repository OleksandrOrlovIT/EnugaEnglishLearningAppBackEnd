package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.BaseEntity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.enums.BookGenre;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Book extends BaseEntity {

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @Enumerated(EnumType.ORDINAL)
    private BookGenre bookGenre;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Page> pages;

    @Builder
    public Book(Long id, String title, String author, BookGenre bookGenre, List<Page> pages) {
        super(id);
        this.title = title;
        this.author = author;
        this.bookGenre = bookGenre;
        this.pages = pages;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Book book = (Book) o;
        return getId() != null && Objects.equals(getId(), book.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "bookGenre = " + getBookGenre() + ", " +
                "author = " + getAuthor() + ", " +
                "title = " + getTitle() + ")";
    }
}
