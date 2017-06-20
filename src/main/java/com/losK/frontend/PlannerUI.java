package com.losK.frontend;

import com.losK.backend.MeetingList;
import com.losK.model.Meeting;
import com.losK.repository.MeetingRepository;
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
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringUI
@Theme("mytheme")
public class PlannerUI extends UI {

    @Autowired
    private MeetingList lastMeetingList;

    private VerticalLayout root;

    private HorizontalLayout centralLayout;

    private Calendar calendar;

    private TextField meetingNameField;

    private TextArea meetingDescriptionArea;

    private DateField meetingStartDateField;

    private NativeSelect meetingDurationNativeSelect;

    private ComboBox roomChoiceBox;

    private Label addingEventHeader;

    private Label durationLabel;

    private Button addingMeetingButton;

    private Button dayButton;

    private Button weekButton;

    private Button monthButton;

    private Date now = new Date();

    MeetingRepository repository;

    @Autowired
    public PlannerUI(MeetingRepository repository) {
        this.repository = repository;
//        this.calendar = new Calendar<>(Meeting.class);
    }

    @Override
    protected void init(VaadinRequest request) {
        this.setLocale(Locale.US);
        setRootLayout();
        addChangingCalendarViewButtons();
        setCenterLayout();
        addCalendar();
        setAddingMeetingLayout();
        addLastMeetingList();
        addDummyEvents();
    }

    private void setRootLayout() {
        root = new VerticalLayout();
        root.setMargin(true);
        setContent(root);
    }

    private void addChangingCalendarViewButtons() {
        HorizontalLayout calendarViewButtonLayout = new HorizontalLayout();
        calendarViewButtonLayout.setSpacing(true);
        setDayButton();
        setWeekButton();
        setMonthButton();
        calendarViewButtonLayout.addComponents(dayButton, weekButton, monthButton);
        root.addComponent(calendarViewButtonLayout);
    }

    private void setMonthButton() {
        monthButton = new Button("Month view");
        monthButton.addClickListener(click -> {
            calendar.setStartDate(now);
            calendar.setEndDate(DateUtils.addMonths(now, 1));
        });
    }

    private void setWeekButton() {
        weekButton = new Button("Week view");
        weekButton.addClickListener(click -> {
            calendar.setStartDate(now);
            calendar.setEndDate(DateUtils.addWeeks(now, 1));
        });
    }

    private void setDayButton() {
        dayButton = new Button("Day view");
        dayButton.addClickListener(click -> {
            calendar.setStartDate(now);
            calendar.setEndDate(DateUtils.addMinutes(now, 15));
        });
    }

    private void setCenterLayout() {
        centralLayout = new HorizontalLayout();
        centralLayout.setSpacing(true);
        root.addComponent(centralLayout);
    }

    private void addCalendar() {
        setCalendar();
        centralLayout.addComponent(calendar);
    }

    private void setCalendar() {
        calendar = new Calendar("My meeting planner");
        calendar.setWidth("750px");
        calendar.setHeight("500px");
        calendar.setStartDate(now);
        calendar.setEndDate(DateUtils.addMonths(now, 1));
    }

    private void setAddingMeetingLayout() {
        VerticalLayout meetingLayout = new VerticalLayout();
        meetingLayout.setSpacing(true);
        meetingLayout.setMargin(true);

        setHeader();
        setMeetingNameField();
        setRoomChoiceBox();
        setMeetingStartDateField();

        HorizontalLayout durationLayout = new HorizontalLayout();
        setMeetingDurationNativeSelect();
        setDurationLabel();
        durationLayout.addComponents(meetingDurationNativeSelect, durationLabel);
        durationLayout.setComponentAlignment(durationLabel, Alignment.BOTTOM_LEFT);

        setMeetingDescriptionArea();
        setAddingMeetingButton();
        setAddingMeetingButtonOnAction();

        meetingLayout.addComponents(addingEventHeader, meetingNameField, roomChoiceBox,
                meetingStartDateField, durationLayout,
                meetingDescriptionArea, addingMeetingButton);

        centralLayout.addComponent(meetingLayout);
    }

    private void setAddingMeetingButton() {
        addingMeetingButton = new Button("Add");
        addingMeetingButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addingMeetingButton.setIcon(FontAwesome.PLUS);
    }

    private void setDurationLabel() {
        durationLabel = new Label("min");
    }

    private void setMeetingDescriptionArea() {
        meetingDescriptionArea = new TextArea();
        meetingDescriptionArea.setInputPrompt("Description");
    }

    private void setMeetingDurationNativeSelect() {
        meetingDurationNativeSelect = new NativeSelect();
        meetingDurationNativeSelect.setCaption("Duration:");
        meetingDurationNativeSelect.setNullSelectionAllowed(false);
        meetingDurationNativeSelect.setRequired(true);
        List<Integer> meetingDurationList = initialMeetingDurationList();
        meetingDurationNativeSelect.addItems(meetingDurationList);
        meetingDurationNativeSelect.setValue(meetingDurationList.iterator().next());
        meetingDurationNativeSelect.setTabIndex(5);
    }

    private void setMeetingStartDateField() {
        meetingStartDateField = new DateField("Start date:");
        Date startMeetingDate = DateUtils.addDays(now, 11);
        meetingStartDateField.setRangeStart(startMeetingDate);
        meetingStartDateField.setRangeEnd
                (new GregorianCalendar(2018, 12, 31, 17, 00, 00)
                        .getTime());
        meetingStartDateField.setValue(startMeetingDate);
        meetingStartDateField.setDateFormat("dd/MM/yyyy hh:mm aa");
        meetingStartDateField.setResolution(Resolution.MINUTE);
        meetingStartDateField.setRequired(true);
    }

    private void setRoomChoiceBox() {
        roomChoiceBox = new ComboBox();
        roomChoiceBox.setInputPrompt("Select room");
        roomChoiceBox.addItems("Red", "Green", "Blue");
        roomChoiceBox.setNullSelectionAllowed(false);
        roomChoiceBox.setTextInputAllowed(false);
        roomChoiceBox.setRequired(true);
    }

    private void setMeetingNameField() {
        meetingNameField = new TextField();
        meetingNameField.focus();
        meetingNameField.setInputPrompt("Name");
        meetingNameField.setRequired(true);
    }

    private void setHeader() {
        addingEventHeader = new Label("Add a meeting");
        addingEventHeader.setSizeUndefined();
        addingEventHeader.addStyleName(ValoTheme.LABEL_H2);
    }

    private void addLastMeetingList() {
        Label lastMeetingHeader = new Label("Recently added meetings to the database:");
        lastMeetingHeader.addStyleName(ValoTheme.LABEL_COLORED);
        lastMeetingHeader.addStyleName(ValoTheme.LABEL_H2);
        root.addComponents(lastMeetingHeader, lastMeetingList);
    }

    private void addDummyEvents() {
//        Date firstEventStart = DateUtils.addMinutes(now, 15);
//        Date firstEventEnd = DateUtils.addMinutes(firstEventStart, 5);
//        BasicEvent firstEvent = new BasicEvent("Interview", "My interview",
//                firstEventStart, firstEventEnd);
        List<Meeting> all = repository.findAll();
        calendar.addEvent(all.get(0));

        Date secondEventStart = DateUtils.addDays(now, 6);
        Date secondEventEnd = DateUtils.addDays(secondEventStart, 4);
        BasicEvent secondEvent = new BasicEvent("Sepia Ensemble Project", "Rehearsals",
                secondEventStart, secondEventEnd);
        secondEvent.setAllDay(true);
        calendar.addEvent(secondEvent);
    }

    private List<Integer> initialMeetingDurationList() {
        return IntStream.iterate(15, n -> n + 5).limit(22)
                .boxed().collect(Collectors.toList());
    }

    private void setAddingMeetingButtonOnAction() {
        addingMeetingButton.addClickListener(click -> {
            Validation validation = new Validation();
            String meetingName = meetingNameField.getValue();
            String meetingDescription = meetingDescriptionArea.getValue();
            Date meetingStartDate = meetingStartDateField.getValue();
            DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
            String meetingRoom = (String) roomChoiceBox.getValue();
            Integer meetingDuration = (Integer) meetingDurationNativeSelect.getValue();
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
        addingMeetingButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    private void createBasicCalendarEvent() {
        BasicEvent test = new BasicEvent(meetingNameField.getValue(), meetingDescriptionArea.getValue(),
                meetingStartDateField.getValue(), DateUtils.addMinutes(meetingStartDateField.getValue(),
                (Integer) meetingDurationNativeSelect.getValue()));
        calendar.addEvent(test);
    }
}
