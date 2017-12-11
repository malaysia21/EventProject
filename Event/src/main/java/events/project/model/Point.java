package events.project.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Point {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="dl")
    private Long longitude;
    @Column(name="sz")
    private Long latitude;

    @Column
    @OneToMany(mappedBy = "point")
    private List<Event> events;

    public Point() {}

    public Point(Long longitude, Long latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }


}
