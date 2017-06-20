package com.losK.view;

import com.losK.service.MeetingListChangeListener;
import com.losK.model.Meeting;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
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

    private TextField roomName;

    public LastEventLayout(Meeting meeting, MeetingListChangeListener changeListener) {
        setSpacing(true);
        setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);

        setCaptionTextFieldStyle();
        setDescriptionTextFieldStyle();
        setStartDateTextFieldStyle();
        setRoomNameTextFieldStyle();
        addMeetingToLastEventGroup(meeting);
        addComponentsToLayout();
        addListener(meeting, changeListener);
    }

    private void addListener(Meeting meeting, MeetingListChangeListener changeListener) {
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

    private void setStartDateTextFieldStyle() {
        start = new TextField();
        start.setInputPrompt("Start date");
        start.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        start.setWidth(220, Unit.PIXELS);
    }

    private void setRoomNameTextFieldStyle() {
        roomName = new TextField();
        roomName.setInputPrompt("Room");
        roomName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        roomName.setWidth(80, Unit.PIXELS);
        roomName.setReadOnly(true);
    }

    private void setCaptionTextFieldStyle() {
        caption = new TextField();
        caption.setInputPrompt("Name");
        caption.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        caption.setWidth(200, Unit.PIXELS);
    }

    private void setDescriptionTextFieldStyle() {
        description = new TextField();
        description.setInputPrompt("Description");
        description.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        description.setWidth(500, Unit.PIXELS);
    }
}
