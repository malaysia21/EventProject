package events.project.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Point {

    @Id
    @GeneratedValue
    private Long id;

    @Digits(integer = 11, fraction = 6)
    @Column(name = "dl")
    private Float longitude;

    @Digits(integer = 11, fraction = 6)
    @Column(name = "sz")
    private Float latitude;

    @Column
    @OneToMany(mappedBy = "point")
    private List<Event> events;

    public Point() {
    }

    public Point(Float longitude, Float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }


}
