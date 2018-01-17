package events.project.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import events.project.validation.CustomDateDeserializer;
import events.project.validation.CustomLocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

public class EventSearching {

    private String name;
    private String eventType;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate date1;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate date2;

    private Point p1;
    private Point p2;

    public EventSearching() {}


    public EventSearching(String name, String eventType, LocalDate date1, LocalDate date2, Point p1, Point p) {
        this.name = name;
        this.eventType = eventType;
        this.date1 = date1;
        this.date2 = date2;
        this.p1 = p1;
        this.p2 = p;
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

    public LocalDate getDate1() {
        return date1;
    }

    public void setDate1(LocalDate date1) {
        this.date1 = date1;
    }

    public LocalDate getDate2() {
        return date2;
    }

    public void setDate2(LocalDate date2) {
        this.date2 = date2;
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP() {
        return p2;
    }

    public void setP(Point p) {
        this.p2 = p;
    }
}
