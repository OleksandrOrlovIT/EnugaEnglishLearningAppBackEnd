package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class QuestionTest {

    private static Question question;

    private static final String QUESTION_TEXT = "Some text";
    private static final String ANSWER = "Answer";

    @BeforeEach
    void setUp(){question = new Question();}

    @Test
    void getSetQuestionText(){
        assertNull(question.getQuestionText());

        question.setQuestionText(QUESTION_TEXT);

        assertEquals(QUESTION_TEXT, question.getQuestionText());
    }

    @Test
    void getSetQuestionAnswer(){
        assertNull(question.getAnswer());

        question.setAnswer(ANSWER);

        assertEquals(ANSWER, question.getAnswer());
    }

    @Test
    void builder(){
        assertNull(question.getQuestionText());
        assertNull(question.getAnswer());

        question = Question
                .builder()
                .questionText(QUESTION_TEXT)
                .answer(ANSWER)
                .build();

        assertEquals(QUESTION_TEXT, question.getQuestionText());
        assertEquals(ANSWER, question.getAnswer());
    }
}
