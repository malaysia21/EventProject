package events.project.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import events.project.users.User;
import events.project.validation.CustomDateDeserializer;
import events.project.validation.CustomLocalDateTimeSerializer;
import events.project.validation.EventTypeConstraint;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotBlank(message = "Name may not be null")
    private String name;

    @Column(name="type")
    @EventTypeConstraint(enumClass = EventType.class, ignoreCase = true)
    @NotNull(message = "Event type may not be null")
    private String eventType;

    @ManyToOne(cascade = CascadeType.ALL)
    private Point point;

    @ManyToOne(cascade = CascadeType.ALL)
    private Adress adress;

    @Column(name="startingTime")
    @NotNull(message = "Starting time may not be null")
    private LocalTime startingTime;

    @Column(name="endingTime")
    @NotNull(message = "Ending time may not be null")
    private LocalTime endingTime;

    @Column(name="date")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @NotNull(message = "Date may not be null")
    private LocalDate date;

    @ManyToOne
    private User user;

    @Column(name="confirmation")
    private boolean confirm;


    public Event() {
    }


    public Event(String name, String eventType, Point point, Adress adress, LocalTime startingTime, LocalTime endingTime, LocalDate date) {
        this.name = name;
        this.eventType = eventType;
        this.point = point;
        this.adress = adress;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.date = date;
        this.user = null;
        this.confirm = false;
    }


    public Event(String name, String eventType, Point point, Adress adress, LocalTime startingTime, LocalTime endingTime, LocalDate date, User user) {
        this.name = name;
        this.eventType = eventType;
        this.point = point;
        this.adress = adress;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.date = date;
        this.confirm = false;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
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

    public static EventBuilder New(){
        return new Event.EventBuilder();
    }
    public static class EventBuilder{

        private Event event;

        private EventBuilder(){
            event = new Event();
        }

        public Event.EventBuilder name(String name){
            event.name=name;
            return this;
        }

        public Event.EventBuilder eventType(String eventType){
            event.eventType=eventType;
            return this;
        }

        public Event.EventBuilder point(Point point){
            event.point=point;
            return this;
        }

        public Event.EventBuilder address(Adress address){
            event.adress=address;
            return this;
        }

        public Event.EventBuilder startingTime(LocalTime startingTime){
            event.startingTime=startingTime;
            return this;
        }

             public Event.EventBuilder endingTime(LocalTime endingTime){
            event.endingTime=endingTime;
            return this;
        }

        public Event.EventBuilder date(LocalDate date){
            event.date=date;
            return this;
        }


        public Event bulid() {
            return event;
        }
    }
}
