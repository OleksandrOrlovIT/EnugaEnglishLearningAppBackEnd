package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.BaseEntity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.engtest.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "app_users")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<TestAttempt> testAttempts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WordModule> wordModules;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<WordModuleAttempt> wordModuleAttempts;

    @Builder
    public User(Long id, String email, String firstName, String lastName, String password, Set<Role> roles,
                List<TestAttempt> testAttempts, List<WordModule> wordModules, List<WordModuleAttempt> wordModuleAttempts) {
        super(id);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = roles;
        this.testAttempts = testAttempts;
        this.wordModules = wordModules;
        this.wordModuleAttempts = wordModuleAttempts;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "email = " + getEmail() + ", " +
                "firstName = " + getFirstName() + ", " +
                "lastName = " + getLastName() + ", " +
                "password = " + getPassword() + ", " +
                "roles = " + getRoles() + ")";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
