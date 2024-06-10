package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.user;

import org.springframework.boot.CommandLineRunner;

public interface UserLoader extends CommandLineRunner {
    void loadUsers();
}
