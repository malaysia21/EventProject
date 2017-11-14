package events.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import events.project.EventTypeConstraint;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Component
@Table(name="events")
public class Event   {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="name")
    @NotBlank(message = "Name can't be empty!")
    private String name;

    @Column(name="type")
    @EventTypeConstraint(enumClass = EventType.class, ignoreCase = true)
    private String eventType;

    @Column(name="date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @Column(name="startingTime")
    private LocalTime startingTime;

    @Column(name="endingTime")
    private LocalTime endingTime;

    public Event() {
    }

    public Event(String name, String eventType) {
        this.name = name;
        this.eventType = eventType;
    }


    public Event(String name, String eventType, LocalDate date, LocalTime startingTime, LocalTime endingTime) {
        this.name = name;
        this.eventType = eventType;
        this.date = date;
        this.startingTime=startingTime;
        this.endingTime=endingTime;
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
