package events.project.modelEntity;
import events.project.validation.CityTypeConstraint;
import javax.persistence.*;
import java.util.List;

/**
 * Encja Address
 * @version 1.1
 */
@Entity
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String street;
    @Column
    @CityTypeConstraint(enumClass = Cities.class, ignoreCase = true)
    private String city;
    @Column
    private String number;
    @Column
    @OneToMany(mappedBy = "address")
    private List<Event> events;

    public Address() {
    }

    public Address(String street, String city, String number) {
        this.street = street;
        this.city = city;
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}
