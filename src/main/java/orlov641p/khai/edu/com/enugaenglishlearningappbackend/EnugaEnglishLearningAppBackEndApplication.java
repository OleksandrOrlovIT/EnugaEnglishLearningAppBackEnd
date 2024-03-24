package orlov641p.khai.edu.com.enugaenglishlearningappbackend;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Rule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.EnglishTestService;

@SpringBootApplication
public class EnugaEnglishLearningAppBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnugaEnglishLearningAppBackEndApplication.class, args);
    }

}