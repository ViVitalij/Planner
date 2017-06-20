package com.losK.model;

import com.losK.service.DateConverter;
import com.vaadin.ui.components.calendar.event.BasicEvent;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by m.losK on 2017-04-23.
 */

@Entity
public class Meeting extends BasicEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String caption;

    private String description;

    @Convert(converter = DateConverter.class)
    private Date startDate;

    @Convert(converter = DateConverter.class)
    private Date meetingEndDate;

    private String roomName;

    public Meeting(String caption, String description, Date startDate, Date meetingEndDate, String roomName) {
        this.caption = caption;
        this.description = description;
        this.startDate = startDate;
        this.meetingEndDate = meetingEndDate;
        this.roomName = roomName;
    }

    public Meeting() {
    }

    @Override
    public void setCaption(String meetingName) {
        this.caption = meetingName;
    }

    @Override
    public void setDescription(String meetingDescription) {
        this.description = meetingDescription;
    }

    @Override
    public void setEnd(Date meetingEndDate) {
        this.meetingEndDate = meetingEndDate;
    }

    @Override
    public void setStart(Date meetingStartDate) {
        this.startDate = meetingStartDate;
    }

    @Override
    public Date getStart() {
        return startDate;
    }

    @Override
    public Date getEnd() {
        return meetingEndDate;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isAllDay() {
        return false;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "caption='" + caption + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", meetingEndDate=" + meetingEndDate +
                '}';
    }
}
