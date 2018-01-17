package events.project.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import events.project.users.User;
import events.project.validation.CustomDateDeserializer;
import events.project.validation.CustomLocalDateTimeSerializer;
import events.project.validation.EventTypeConstraint;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;



public class EventDto implements Serializable {

    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @EventTypeConstraint(enumClass = EventType.class, ignoreCase = true)
    private String eventType;

    private Point point;

    private Adress adress;

    @NotNull(message = "Starting time cannot be empty")
    private LocalTime startingTime;

    @NotNull(message = "Ending time cannot be empty")
    private LocalTime endingTime;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @NotNull(message = "Date cannot be empty")
    private LocalDate date;


    public EventDto() {
    }


    public EventDto(String name, String eventType, Point point, Adress adress, LocalTime startingTime, LocalTime endingTime, LocalDate date) {
        this.name = name;
        this.eventType = eventType;
        this.point = point;
        this.adress = adress;
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

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
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
                ", name='" + name + '\'' +
                ", eventType=" + eventType +
                ", date=" + date +
                ", startingTime=" + startingTime +
                ", endingTime=" + endingTime +
                '}';
    }

}
