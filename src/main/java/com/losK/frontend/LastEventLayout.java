package com.losK.frontend;

import com.losK.backend.MeetingChangeListener;
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
    private final TextField caption;
    private final TextField description;
    private final TextField start;
    private final TextField end;
    private final TextField roomName;
    private final TextField allDay;
    private final TextField styleName;

    public LastEventLayout(Meeting meeting, MeetingChangeListener changeListener) {
        setSpacing(true);
        setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);

        caption = new TextField();
        caption.setInputPrompt("Meeting name");
        caption.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        caption.setWidth(200, Unit.PIXELS);

        description = new TextField();
        description.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        description.setWidth(500, Unit.PIXELS);

        start = new TextField();
        start.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        start.setWidth(220, Unit.PIXELS);

        end = new TextField();

        roomName = new TextField();
        roomName.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
        roomName.setWidth(80, Unit.PIXELS);
        roomName.setReadOnly(true);

        allDay = new TextField();
        styleName = new TextField();

        FieldGroup lastEventsGroup = new FieldGroup(new BeanItem<>(meeting));
        lastEventsGroup.bindMemberFields(this);
        lastEventsGroup.setBuffered(false);

        addComponents(caption, start, roomName, description);
        Arrays.asList(caption, roomName, description).forEach(field ->
                field.addValueChangeListener(change ->
                        changeListener.eventChange(meeting)));
    }
}
