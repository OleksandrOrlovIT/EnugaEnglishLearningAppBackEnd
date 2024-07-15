package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.BaseEntity;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class UkrainianWord extends BaseEntity {

    @Column(unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8_bin", nullable = false)
    private String word;

    @Builder
    public UkrainianWord(Long id, String word) {
        super(id);
        this.word = word;
    }

    public UkrainianWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UkrainianWord that = (UkrainianWord) o;
        return Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(word);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "word = " + getWord() + ")";
    }
}
