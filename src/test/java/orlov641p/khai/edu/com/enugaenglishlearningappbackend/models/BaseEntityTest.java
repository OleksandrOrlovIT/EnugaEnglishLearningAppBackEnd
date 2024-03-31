package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BaseEntityTest {
    private static BaseEntity baseEntity;

    @BeforeEach
    void setUp() {
        baseEntity = Rule.builder().id(1L).build();
    }

    @Test
    void equals_SameObject(){
        assertEquals(baseEntity, baseEntity);
    }

    @Test
    void equals_null(){
        assertNotEquals(baseEntity, null);
    }

    @Test
    void equals_differentClass(){
        assertNotEquals(baseEntity, Question.builder().build());
    }
}
