package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    private static Question question;
    private static EnglishTest englishTest;

    private static final Long ID = 1L;
    private static final String QUESTION_TEXT = "Question text";
    private static final String ANSWER = "Answer";

    @BeforeEach
    void setUp(){
        question = Question.builder()
            .id(ID)
            .questionText(QUESTION_TEXT)
            .answer(ANSWER)
            .build();
    }

    @Test
    void getSetId(){
        Long tempId = 2L;

        assertEquals(ID, question.getId());

        question.setId(tempId);

        assertEquals(tempId, question.getId());
    }

    @Test
    void getSetQuestionText(){
        String tempText = "asd";

        assertEquals(QUESTION_TEXT, question.getQuestionText());

        question.setQuestionText(tempText);

        assertEquals(tempText, question.getQuestionText());
    }

    @Test
    void getSetQuestionAnswer(){
        String tempAnswer = "asd";

        assertEquals(ANSWER, question.getAnswer());

        question.setAnswer(tempAnswer);

        assertEquals(tempAnswer, question.getAnswer());
    }

    @Test
    void getSetEnglishTest(){
        assertNull(question.getEnglishTest());

        englishTest = new EnglishTest();
        question.setEnglishTest(englishTest);

        assertEquals(englishTest, question.getEnglishTest());
    }

    @Test
    void builder(){
        englishTest = new EnglishTest();
        question = Question.builder()
                .id(ID)
                .questionText(QUESTION_TEXT)
                .answer(ANSWER)
                .englishTest(englishTest)
                .build();

        assertEquals(QUESTION_TEXT, question.getQuestionText());
        assertEquals(ANSWER, question.getAnswer());
        assertEquals(englishTest, question.getEnglishTest());
    }

    @Test
    void testHashcode_Id(){
        Long tempId = 2L;

        Question tempQuestion = Question.builder().id(ID).build();
        Question tempQuestion2 = Question.builder().id(ID).build();
        assertEquals(tempQuestion.hashCode(), tempQuestion2.hashCode());

        tempQuestion.setId(tempId);

        assertNotEquals(tempQuestion.hashCode(), tempQuestion2.hashCode());
    }

    @Test
    void testHashcode_QuestionText(){
        String tempQuestionText = "Some text";

        Question tempQuestion = Question.builder().id(ID).questionText(QUESTION_TEXT).build();
        Question tempQuestion2 = Question.builder().id(ID).questionText(QUESTION_TEXT).build();
        assertEquals(tempQuestion.hashCode(), tempQuestion2.hashCode());

        tempQuestion.setQuestionText(tempQuestionText);

        assertNotEquals(tempQuestion.hashCode(), tempQuestion2.hashCode());
    }

    @Test
    void testHashcode_Answer(){
        String tempAnswer = "Some text";

        question.setQuestionText(QUESTION_TEXT);
        Question tempQuestion = Question.builder().id(ID).questionText(QUESTION_TEXT).answer(ANSWER).build();
        assertEquals(question.hashCode(), tempQuestion.hashCode());

        tempQuestion.setAnswer(tempAnswer);

        assertNotEquals(question.hashCode(), tempQuestion.hashCode());
    }

    @Test
    void testEquals_ID(){
        Question question1 = Question.builder().id(ID).build();
        Question question2 = Question.builder().id(ID).build();

        assertEquals(question1, question2);

        question1.setId(question2.getId() + 1);

        assertNotEquals(question1, question2);
    }

    @Test
    void testEquals_ID_QuestionText(){
        Question question1 = Question.builder().id(ID).questionText(QUESTION_TEXT).build();
        Question question2 = Question.builder().id(ID).questionText(QUESTION_TEXT).build();

        assertEquals(question1, question2);

        question1.setQuestionText(question2.getQuestionText() + "1");
        assertNotEquals(question1, question2);
    }

    @Test
    void testEquals_ID_QuestionText_Answer(){
        Question question1 = Question.builder().id(ID).questionText(QUESTION_TEXT).answer(ANSWER).build();
        assertEquals(question1, question);

        question1.setAnswer(question.getAnswer() + "1");
        assertNotEquals(question1, question);
    }

    @Test
    void questionToString(){
        String expected = "Question{id=1, questionText='Question text', answer='Answer'}";

        assertEquals(expected, question.toString());
    }

    @Test
    void questionBuilderToString(){
        englishTest = EnglishTest.builder().testName("ASD").build();

        Question.QuestionBuilder questionBuilder = Question.builder()
                .id(ID)
                .questionText(QUESTION_TEXT)
                .answer(ANSWER)
                .englishTest(englishTest);

        String expected = "Question.QuestionBuilder(id=1, questionText=Question text," +
                " answer=Answer, englishTest=EnglishTest{id=null, testName='ASD'})";

        assertEquals(expected, questionBuilder.toString());
    }
}