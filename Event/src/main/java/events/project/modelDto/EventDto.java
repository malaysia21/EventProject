package events.project.modelDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import events.project.modelEntity.EventType;
import events.project.modelEntity.Address;
import events.project.modelEntity.Point;
import events.project.validation.EventTypeConstraint;
import lombok.Builder;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Data Transfer Objects dla encji Event
 * @version 1.1
 */
@Builder
public class EventDto {

    @NotBlank(message = "Name cannot be empty!")
    private String name;
    @EventTypeConstraint(enumClass = EventType.class, ignoreCase = true)
    private String eventType;
    private Point point;
    private Address address;
    @Column(name = "beginning")
    @NotNull(message = "Beginning time may not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime beginningDateTime;
    @Column(name = "ending")
    @NotNull(message = "Ending time may not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime endingDateTime;
    private Long userId;
    private boolean confirm;

    public EventDto() {
    }

    public EventDto(String name, String eventType, Point point, Address address, LocalDateTime beginningDateTime, LocalDateTime endingDateTime, Long userId, boolean confirm) {
        this.name = name;
        this.eventType = eventType;
        this.point = point;
        this.address = address;
        this.beginningDateTime = beginningDateTime;
        this.endingDateTime = endingDateTime;
        this.userId = userId;
        this.confirm = confirm;
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
}