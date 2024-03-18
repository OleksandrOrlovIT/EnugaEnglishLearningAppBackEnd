package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String description;

    @Builder
    public Rule(Long id, String ruleName, String description) {
        super(id);
        this.ruleName = ruleName;
        this.description = description;
    }
}
