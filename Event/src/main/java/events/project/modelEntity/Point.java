package events.project.modelEntity;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.util.List;
/**
 * Encja Point
 * @version 1.1
 */
@Entity
public class Point {

    @Id
    @GeneratedValue
    private Long id;
    @Digits(integer = 11, fraction = 6)
    @Column(name = "lon")
    private Float longitude;
    @Digits(integer = 11, fraction = 6)
    @Column(name = "lat")
    private Float latitude;
    @Column(name = "drag")
    private boolean draggable;
    @OneToMany(mappedBy = "point")
    private List<Event> events;

    public Point() {
    }

    public Point(Float longitude, Float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.draggable = true;
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

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }
}
