package com.losK;

import com.losK.model.Meeting;
import com.losK.repository.MeetingRepository;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class PlannerApplication {

    private static final Logger log = LoggerFactory.getLogger(PlannerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PlannerApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(MeetingRepository repository) {
        return (args) -> {
            List<Meeting> meetingsList = initMeetingsList();
            repository.save(meetingsList);

            for (Meeting customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");
        };
    }

    private List<Meeting> initMeetingsList(){
        List<Meeting> meetingsList = new ArrayList<>();
        Meeting interview = getFirstMeeting();
        Meeting rehearsal = getSecondMeeting();
        meetingsList.add(interview);
        meetingsList.add(rehearsal);
        return meetingsList;
    }

    private Meeting getSecondMeeting() {
        Date secondEventStart = DateUtils.addDays(new Date(), 6);
        Date secondEventEnd = DateUtils.addDays(secondEventStart, 4);
        return new Meeting("Sepia Ensemble Project", "Rehearsals",
                secondEventStart, secondEventEnd, "Red");
    }

    private Meeting getFirstMeeting() {
        Date firstEventStart = DateUtils.addMinutes(new Date(), 15);
        Date firstEventEnd = DateUtils.addMinutes(firstEventStart, 5);
        return new Meeting("Interview", "MyInterview", firstEventStart, firstEventEnd, "Green");
    }

}