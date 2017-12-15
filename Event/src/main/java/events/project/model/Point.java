package events.project.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Point {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="dl")
    private long longitude;
    @Column(name="sz")
    private long latitude;

    @Column
    @OneToMany(mappedBy = "point")
    private List<Event> events;

    public Point() {}

    public Point(long longitude, long latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }


}
