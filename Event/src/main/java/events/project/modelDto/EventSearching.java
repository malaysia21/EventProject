package events.project.modelDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import events.project.modelEntity.Point;
import java.time.LocalDate;
/**
 * Obiekt zawiera kryteria wyszukiania wydarzeń
 * @version 1.1
 */
public class EventSearching {

    private String name;
    private String eventType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    private Point point1;
    private Point point2;

    public EventSearching() {}


    public EventSearching(String name, String eventType, LocalDate date, Point point1, Point point2) {
        this.name = name;
        this.eventType = eventType;
        this.date = date;
        this.point1 = point1;
        this.point2 = point2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate1(LocalDate date1) {
        this.date = date;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }
}
