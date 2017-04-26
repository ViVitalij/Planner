package com.losK.frontend;

import com.losK.backend.MeetingList;
import com.losK.model.Meeting;
import com.losK.validation.Validation;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to save component to the user interface and initialize non-component functionality.
 */
@SpringUI
@Theme("mytheme")
public class PlannerUI extends UI {

    private VerticalLayout root;
    private HorizontalLayout centralLayout;
    private HorizontalLayout durationLayout;

    @Autowired
    MeetingList lastMeetingList;

    private Calendar calendar;
    private TextField meetingNameField;
    private TextArea meetingDescriptionArea;
    private DateField meetingStartDateField;
    private Button addMeetingButton;
    private NativeSelect meetingDurationSelect;
    private ComboBox roomChoiceBox;
    private Label addEventHeader;
    private Label lastMeetingHeader;
    private Label durationLabel;

    @Override
    protected void init(VaadinRequest request) {
        this.setLocale(Locale.US);
        setRootLayout();
        addViewButtons();
        setCenterLayout();
        addCalendar();
        setAddMeetingLayout();
        addLastMeetingList();
        addDummyEvents();

    }

    private void setRootLayout() {
        root = new VerticalLayout();
        root.setMargin(true);
        setContent(root);
    }

    private void addViewButtons() {
        HorizontalLayout viewButtonLayout = new HorizontalLayout();
        viewButtonLayout.setSpacing(true);
        Date startDate = new Date();
        Button buttonDay = new Button("Day view");
        buttonDay.addClickListener(click -> {
            calendar.setStartDate(startDate);
            calendar.setEndDate(DateUtils.addMinutes(startDate, 15));
//            calendar.autoScaleVisibleHoursOfDay();
        });
        Button buttonWeek = new Button("Week view");
        buttonWeek.addClickListener(click -> {
            calendar.setStartDate(startDate);
            calendar.setEndDate(DateUtils.addWeeks(startDate, 1));
        });
        Button buttonMonth = new Button("Month view");
        buttonMonth.addClickListener(click -> {
            calendar.setStartDate(startDate);
            calendar.setEndDate(DateUtils.addMonths(startDate, 1));
        });

        viewButtonLayout.addComponents(buttonDay, buttonWeek, buttonMonth);
        root.addComponent(viewButtonLayout);
    }

    private void setCenterLayout() {
        centralLayout = new HorizontalLayout();
        centralLayout.setSpacing(true);
        root.addComponent(centralLayout);
    }

    private void addCalendar() {
        calendar = new Calendar("My Planner");
        calendar.setWidth("750px");
        calendar.setHeight("500px");
        Date startDate = new Date();
        calendar.setStartDate(startDate);
        calendar.setEndDate(DateUtils.addMonths(startDate, 1));
        centralLayout.addComponent(calendar);
    }

    private void setAddMeetingLayout() {
        VerticalLayout meetingLayout = new VerticalLayout();
        meetingLayout.setSpacing(true);
        meetingLayout.setMargin(true);

        addEventHeader = new Label("Add a meeting");
        addEventHeader.setSizeUndefined();
        addEventHeader.addStyleName(ValoTheme.LABEL_H2);

        meetingNameField = new TextField();
        meetingNameField.focus();
        meetingNameField.setInputPrompt("Name");
        meetingNameField.setRequired(true);

        roomChoiceBox = new ComboBox();
        roomChoiceBox.setInputPrompt("Select room");
        roomChoiceBox.addItems("Red", "Green", "Blue");
        roomChoiceBox.setNullSelectionAllowed(false);
        roomChoiceBox.setTextInputAllowed(false);
        roomChoiceBox.setRequired(true);


        meetingStartDateField = new DateField("Start date:");
        Date startMeetingDate = DateUtils.addDays(new Date(), 11);
        meetingStartDateField.setRangeStart(startMeetingDate);
        meetingStartDateField.setRangeEnd
                (new GregorianCalendar(2018, 12, 31, 17, 00, 00).getTime());
        meetingStartDateField.setValue(startMeetingDate);
        meetingStartDateField.setDateFormat("dd/MM/yyyy hh:mm aa");
        meetingStartDateField.setResolution(Resolution.MINUTE);
        meetingStartDateField.setRequired(true);

        durationLayout = new HorizontalLayout();
        meetingDurationSelect = new NativeSelect();
        meetingDurationSelect.setCaption("Duration:");
        meetingDurationSelect.setNullSelectionAllowed(false);
        meetingDurationSelect.setRequired(true);
        List<Integer> meetingDurationList = initialMeetingDurationList();
        meetingDurationSelect.addItems(meetingDurationList);
        meetingDurationSelect.setValue(meetingDurationList.iterator().next());
        meetingDurationSelect.setTabIndex(5);
        durationLabel = new Label("min");
        durationLayout.addComponents(meetingDurationSelect, durationLabel);
        durationLayout.setComponentAlignment(durationLabel, Alignment.BOTTOM_LEFT);

        meetingDescriptionArea = new TextArea();
        meetingDescriptionArea.setInputPrompt("Description");

        addMeetingButton = new Button("Add");
        addMeetingButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addMeetingButton.setIcon(FontAwesome.PLUS);
        addEventButtonOnAction();

        meetingLayout.addComponents(addEventHeader, meetingNameField, roomChoiceBox,
                meetingStartDateField, durationLayout,
                meetingDescriptionArea, addMeetingButton);

        centralLayout.addComponent(meetingLayout);
    }

    private void addLastMeetingList() {
        lastMeetingHeader = new Label("Recently added meetings to the database:");
        lastMeetingHeader.addStyleName(ValoTheme.LABEL_COLORED);
        lastMeetingHeader.addStyleName(ValoTheme.LABEL_H2);
        root.addComponents(lastMeetingHeader, lastMeetingList);
    }

    private void addDummyEvents() {
        Date thisMoment = new Date();
        Date firstEventStart = DateUtils.addMinutes(thisMoment, 15);
        Date firstEventEnd = DateUtils.addMinutes(firstEventStart, 5);
        BasicEvent firstEvent = new BasicEvent("Sepia Ensemble", "Rehearsal",
                firstEventStart, firstEventEnd);
        calendar.addEvent(firstEvent);

        Date secondEventStart = DateUtils.addDays(thisMoment, 6);
        Date secondEventEnd = DateUtils.addDays(secondEventStart, 4);
        BasicEvent secondEvent = new BasicEvent("Cognifide", "Cognifide interview",
                secondEventStart, secondEventEnd);
        secondEvent.setAllDay(true);
        //TODO skasowac albo naprawic
        calendar.addEvent(secondEvent);
    }

    private List<Integer> initialMeetingDurationList() {
        return IntStream.iterate(15, n -> n + 5).limit(22)
                .boxed().collect(Collectors.toList());
    }

    private void addEventButtonOnAction() {
        addMeetingButton.addClickListener(click -> {
            Validation validation = new Validation();
            String meetingName = meetingNameField.getValue();
            String meetingDescription = meetingDescriptionArea.getValue();
            Date meetingStartDate = meetingStartDateField.getValue();
            DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
            String meetingRoom = (String) roomChoiceBox.getValue();
            Integer meetingDuration = (Integer) meetingDurationSelect.getValue();
            Date meetingEndDate = DateUtils.addMinutes(meetingStartDate, meetingDuration);

            if (validation.validateMeetingName(meetingNameField) && validation.validateMeetingRoom(roomChoiceBox)) {

                List<CalendarEvent> userDateEvents = calendar.getEvents(meetingStartDate,
                        DateUtils.addMinutes(meetingStartDate, meetingDuration));

                validation.isRoomFree(meetingRoom, meetingStartDate, meetingDuration, userDateEvents);
                createBasicCalendarEvent();
                lastMeetingList.save(new Meeting(meetingName, meetingDescription,
                        meetingStartDate, meetingEndDate, meetingRoom, false, "mytheme"));
                meetingNameField.clear();
                meetingDescriptionArea.clear();
                meetingNameField.focus();
                meetingNameField.setComponentError(null);
                roomChoiceBox.setComponentError(null);
            }
        });

        meetingNameField.focus();
        addMeetingButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    private void createBasicCalendarEvent() {
        BasicEvent test = new BasicEvent(meetingNameField.getValue(), meetingDescriptionArea.getValue(),
                meetingStartDateField.getValue(), DateUtils.addMinutes(meetingStartDateField.getValue(),
                (Integer) meetingDurationSelect.getValue()));
        calendar.addEvent(test);
    }
}
