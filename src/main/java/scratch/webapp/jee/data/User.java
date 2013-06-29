package scratch.webapp.jee.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.persistence.GenerationType.AUTO;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;

/**
 * A simple user class that contains an email, first, and last names. The email has been annotated to indicate it should
 * be unique. All attributes have been annotated to be not null.
 *
 * @author Karl Bennett
 */
@Entity
@XmlRootElement
@XmlAccessorType(FIELD)
public class User {

    @Id
    @GeneratedValue(strategy = AUTO)
    @XmlElement(required = true)
    private Long id;

    @NotNull(message = "email.null")
    @Column(unique = true, nullable = false)
    @XmlElement(required = true)
    private String email;

    @NotNull(message = "firstName.null")
    @Column(nullable = false)
    @XmlElement(required = true)
    private String firstName;

    @NotNull(message = "lastName.null")
    @Column(nullable = false)
    @XmlElement(required = true)
    private String lastName;


    /**
     * A default constructor is required by serialisation and ORM API's.
     */
    public User() {
    }

    /**
     * Create a new populated {@code User}.
     *
     * @param email     the users email.
     * @param firstName the users first name.
     * @param lastName  the users last name.
     */
    public User(String email, String firstName, String lastName) {

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
