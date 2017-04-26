package com.losK.backend;

import com.losK.frontend.LastEventLayout;
import com.losK.model.Meeting;
import com.losK.repository.MeetingRepository;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Created by m.losK on 2017-04-23.
 */
@UIScope
@SpringComponent
public class MeetingList extends VerticalLayout implements MeetingChangeListener {

    @Autowired
    MeetingRepository repository;
    private List<Meeting> meetings;

    @PostConstruct
    void init() {
        setSpacing(true);
        update();
    }

    private void update() {
        setMeetingList(repository.findAll());
    }

    private void setMeetingList(List<Meeting> meetings) {
        this.meetings = meetings;
        removeAllComponents();
        meetings.forEach(event -> {
            addComponent(new LastEventLayout(event, this));
        });
    }

    public void save(Meeting meeting) {
        try {
            repository.save(meeting);
        } catch (ConstraintViolationException exception) {
            Notification.show("At least 3 characters (max.50)", Notification.Type.ERROR_MESSAGE);
        }
        update();
    }

    @Override
    public void eventChange(Meeting meeting) {
        save(meeting);
    }
}
