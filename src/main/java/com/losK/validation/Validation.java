package com.losK.validation;

import com.vaadin.server.UserError;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

/**
 * Created by m.losK on 2017-04-25.
 */
public class Validation {
    public boolean validateMeetingName(TextField meetingNameField) {
        boolean result = false;
        String meetingName = meetingNameField.getValue();
        if (meetingName == null || meetingName.equals("")) {
            meetingNameField.setComponentError(new UserError("Name is required"));
            Notification.show("Please, enter name", Notification.Type.ERROR_MESSAGE);
        } else {
            result = true;
        }
        return result;
    }

    public boolean validateMeetingRoom(ComboBox roomChoiceBox) {
        boolean result = false;
        String meetingRoom = (String) roomChoiceBox.getValue();
        if (meetingRoom == null || meetingRoom.equals("")) {
            roomChoiceBox.setComponentError(new UserError("Specified room is required"));
            Notification.show("Please, choose room", Notification.Type.ERROR_MESSAGE);
        } else {
            result = true;
        }
        return result;
    }
}
