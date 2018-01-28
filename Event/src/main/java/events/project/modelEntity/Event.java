package events.project.modelEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import events.project.validation.EventTypeConstraint;
import lombok.Builder;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Encja Event
 * @version 1.1
 */
@Entity
@Component
@Table(name = "events")
public class Event implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    @NotBlank(message = "Name may not be null")
    private String name;
    @Column(name = "type")
    @EventTypeConstraint(enumClass = EventType.class, ignoreCase = true)
    @NotNull(message = "Event type may not be null")
    private String eventType;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Point point;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Address address;
    @Column(name = "beginning")
    @NotNull(message = "Beginning time may not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime beginningDateTime;
    @Column(name = "ending")
    @NotNull(message = "Ending time may not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime endingDateTime;
    @ManyToOne
    private User user;
    @Column(name = "confirmation")
    private boolean confirm;


    public Event() {
    }

    @Builder
    public Event(String name, String eventType, Point point, Address address, LocalDateTime beginningDateTime, LocalDateTime endingDateTime, User user) {
        this.name = name;
        this.eventType = eventType;
        this.point = point;
        this.address = address;
        this.beginningDateTime = beginningDateTime;
        this.endingDateTime = endingDateTime;
        this.user = user;
        this.confirm = false;
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

    public LocalDateTime getBeginningDateTime() {
        return beginningDateTime;
    }

    public void setBeginningDateTime(LocalDateTime beginningDateTime) {
        this.beginningDateTime = beginningDateTime;
    }

    public LocalDateTime getEndingDateTime() {
        return endingDateTime;
    }

    public void setEndingDateTime(LocalDateTime endingDateTime) {
        this.endingDateTime = endingDateTime;
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
}
