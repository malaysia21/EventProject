package events.project.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import events.project.validation.CustomDateDeserializer;
import events.project.validation.CustomLocalDateTimeSerializer;
import events.project.validation.EventTypeConstraint;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


public class EventDto {

    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @EventTypeConstraint(enumClass = EventType.class, ignoreCase = true)
    private String eventType;

    private Point point;

    private Address address;

    @NotNull(message = "Starting time cannot be empty")
    private LocalTime startingTime;

    @NotNull(message = "Ending time cannot be empty")
    private LocalTime endingTime;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @NotNull(message = "Date cannot be empty")
    private LocalDate date;

    private Long userId;

    private boolean confirm;

    public EventDto() {
    }


    public EventDto(String name, String eventType, Point point, Address address, LocalTime startingTime, LocalTime endingTime, LocalDate date) {
        this.name = name;
        this.eventType = eventType;
        this.point = point;
        this.address = address;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.date = date;
    }

    public EventDto(String name, String eventType, Point point, Address address,
                    LocalTime startingTime, LocalTime endingTime, LocalDate date, Long userId, boolean confirm) {
        this.name = name;
        this.eventType = eventType;
        this.point = point;
        this.address = address;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.date = date;
        this.userId = userId;
        this.confirm = confirm;
    }


    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
                ", name='" + name + '\'' +
                ", eventType=" + eventType +
                ", date=" + date +
                ", startingTime=" + startingTime +
                ", endingTime=" + endingTime +
                '}';
    }

    public static EventBuilder New() {
        return new EventDto.EventBuilder();
    }

    public static class EventBuilder {

        private EventDto event;

        private EventBuilder() {
            event = new EventDto();
        }

        public EventDto.EventBuilder name(String name) {
            event.name = name;
            return this;
        }

        public EventDto.EventBuilder eventType(String eventType) {
            event.eventType = eventType;
            return this;
        }

        public EventDto.EventBuilder point(Point point) {
            event.point = point;
            return this;
        }

        public EventDto.EventBuilder address(Address address) {
            event.address = address;
            return this;
        }

        public EventDto.EventBuilder startingTime(LocalTime startingTime) {
            event.startingTime = startingTime;
            return this;
        }

        public EventDto.EventBuilder endingTime(LocalTime endingTime) {
            event.endingTime = endingTime;
            return this;
        }

        public EventDto.EventBuilder date(LocalDate date) {
            event.date = date;
            return this;
        }


        public EventDto.EventBuilder userId(Long userId) {
            event.userId = userId;
            return this;
        }


        public EventDto.EventBuilder confirm(boolean confirm) {
            event.confirm = confirm;
            return this;
        }

        public EventDto build() {
            return event;
        }
    }
}