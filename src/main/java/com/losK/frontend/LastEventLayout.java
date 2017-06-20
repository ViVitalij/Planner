package com.losK.frontend;

import com.losK.backend.MeetingChangeListener;
import com.losK.model.Meeting;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Arrays;

/**
 * Created by m.losK on 2017-04-23.
 */
public class LastEventLayout extends HorizontalLayout {

    private TextField caption;

    private TextField description;

    private TextField start;

    private TextField end;

    private TextField roomName;

    private TextField allDay;

    private TextField styleName;

    public LastEventLayout(TextField caption, TextField description, TextField start, TextField end, TextField roomName, TextField allDay, TextField styleName, Component... children) {
        super(children);
        this.caption = caption;
        this.description = description;
        this.start = start;
        this.end = end;
        this.roomName = roomName;
        this.allDay = allDay;
        this.styleName = styleName;
    }

    public LastEventLayout(Meeting meeting, MeetingChangeListener changeListener) {
        setSpacing(true);
        setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);

        setCaptionTextFieldStyle();
        setDescriptionTextFieldStyle();
        setStartDateTextFieldStyle();
        end = new TextField();
        setRoomNameTextFieldStyle();
        allDay = new TextField();
        styleName = new TextField();
        addMeetingToLastEventGroup(meeting);
        addComponentsToLayout();
        addListener(meeting, changeListener);
    }

    private void addListener(Meeting meeting, MeetingChangeListener changeListener) {
        Arrays.asList(caption, roomName, description).forEach(field ->
                field.addValueChangeListener(change ->
                        changeListener.eventChange(meeting)));
    }

    private void addMeetingToLastEventGroup(Meeting meeting) {
        FieldGroup lastEventsGroup = new FieldGroup(new BeanItem<>(meeting));
        lastEventsGroup.bindMemberFields(this);
        lastEventsGroup.setBuffered(false);
    }

    private void addComponentsToLayout() {
        addComponents(caption, start, roomName, description);
    }

    private void setRoomNameTextFieldStyle() {
        roomName = new TextField();
        roomName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        roomName.setWidth(80, Unit.PIXELS);
        roomName.setReadOnly(true);
    }

    private void setStartDateTextFieldStyle() {
        start = new TextField();
        start.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        start.setWidth(220, Unit.PIXELS);
    }

    private void setDescriptionTextFieldStyle() {
        description = new TextField();
        description.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        description.setWidth(500, Unit.PIXELS);
    }

    private void setCaptionTextFieldStyle() {
        caption = new TextField();
        caption.setInputPrompt("Meeting name");
        caption.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        caption.setWidth(200, Unit.PIXELS);
    }
}
