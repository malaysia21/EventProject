package events.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import events.project.validation.CustomDateDeserializer;
import events.project.validation.CustomLocalDateTimeSerializer;
import events.project.validation.DateConstraint;
import events.project.validation.EventTypeConstraint;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Component
@Table(name="events")
public class Event  implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="name")
    @NotBlank(message = "Name can't be empty!")
    private String name;

    @Column(name="type")
    @EventTypeConstraint(enumClass = EventType.class, ignoreCase = true)
    private String eventType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Point point;

    @Column(name="startingTime")
    private LocalTime startingTime;

    @Column(name="endingTime")
    private LocalTime endingTime;

    private long x;    @Column(name="date")
    //@JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @DateConstraint()
    private LocalDate date;


    public Event() {
    }


    public Event(String name, String eventType, Point point, LocalTime startingTime, LocalTime endingTime, LocalDate date) {
        this.name = name;
        this.eventType = eventType;
        this.point = point;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.date = date;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalTime startingTime) {
        this.startingTime = startingTime;
    }

    public LocalTime getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(LocalTime endingTime) {
        this.endingTime = endingTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eventType=" + eventType +
                ", date=" + date +
                ", startingTime=" + startingTime +
                ", endingTime=" + endingTime +
                '}';
    }
}
