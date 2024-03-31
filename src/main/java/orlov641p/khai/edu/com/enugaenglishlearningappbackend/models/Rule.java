package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * A Rule entity representing English grammar rules.
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "rules")
public class Rule extends BaseEntity{

    private String ruleName;

    @Column(length = 1000)
    private String description;

    @Builder
    public Rule(Long id, String ruleName, String description) {
        super(id);
        this.ruleName = ruleName;
        this.description = description;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) return false;

        Rule rule = (Rule) object;

        if (!Objects.equals(ruleName, rule.ruleName)) return false;
        return Objects.equals(description, rule.description);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (ruleName != null ? ruleName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "id=" + getId() +
                ", ruleName='" + ruleName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
