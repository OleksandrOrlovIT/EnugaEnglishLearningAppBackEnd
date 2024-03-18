package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * A main superclass to extend id generations for models.
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}