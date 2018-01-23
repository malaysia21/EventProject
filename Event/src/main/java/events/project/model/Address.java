package events.project.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String street;
    @Column
    private String city;
    @Column
    private int number;

    @Column
    @OneToMany(mappedBy = "address")
    private List<Event> events;

    public Address(String street, String city, int number) {
        this.street = street;
        this.city = city;
        this.number = number;
    }

    public Address() {

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


}
