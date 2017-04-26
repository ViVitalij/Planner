CREATE TABLE IF NOT EXISTS Meeting(id IDENTITY PRIMARY KEY, caption VARCHAR(255),description VARCHAR(255),
                                    meetingRoom VARCHAR(255),startDate VARCHAR(255),meetingEndDate VARCHAR(255),
                                    isAllDay VARCHAR(255),styleName VARCHAR(255));
DELETE FROM Meeting;