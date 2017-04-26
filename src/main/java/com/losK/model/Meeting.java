package com.losK.model;

import com.losK.backend.BooleanConverter;
import com.losK.backend.DateConverter;
import com.vaadin.ui.components.calendar.event.EditableCalendarEvent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by m.losK on 2017-04-23.
 */
@Entity
public class Meeting implements EditableCalendarEvent, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = "Name is required")
    @Size(min = 3, max = 50, message = "At least 3 characters (max.50)")
    private String caption;
    private String description;
    //    @Temporal(TemporalType.TIMESTAMP)
    @Convert(converter = DateConverter.class)
    private Date startDate;
    //    @Temporal(TemporalType.TIMESTAMP)
    @Convert(converter = DateConverter.class)
    private Date meetingEndDate;
    private String roomName;

    @Convert(converter = BooleanConverter.class)
    private Boolean isAllDay;
    private String styleName;

    public Meeting() {
    }

    public Meeting(String caption, String description, Date startDate, Date meetingEndDate) {
        this.caption = caption;
        this.description = description;
        this.startDate = startDate;
        this.meetingEndDate = meetingEndDate;
    }

    public Meeting(String caption, String description, Date startDate, Date meetingEndDate, String roomName) {
        this.caption = caption;
        this.description = description;
        this.startDate = startDate;
        this.meetingEndDate = meetingEndDate;
        this.roomName = roomName;
    }

    public Meeting(String caption, String description, Date startDate,
                   Date meetingEndDate, String roomName, Boolean isAllDay, String styleName) {
        this.caption = caption;
        this.description = description;
        this.startDate = startDate;
        this.meetingEndDate = meetingEndDate;
        this.roomName = roomName;
        this.isAllDay = isAllDay;
        this.styleName = styleName;
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
    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    @Override
    public void setAllDay(boolean isAllDay) {
        this.isAllDay = isAllDay;
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
    public String getStyleName() {
        return styleName;
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
}
