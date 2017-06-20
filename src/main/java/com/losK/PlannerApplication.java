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

import java.util.Date;

@SpringBootApplication
public class PlannerApplication {

    private static final Logger log = LoggerFactory.getLogger(PlannerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PlannerApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(MeetingRepository repository) {
        return (args) -> {
            // save a couple of customers

            Date firstEventStart = DateUtils.addMinutes(new Date(), 15);
            Date firstEventEnd = DateUtils.addMinutes(firstEventStart, 5);


            repository.save(new Meeting("Interview", "MyInterview", firstEventStart, firstEventEnd));
            for (Meeting customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");
        };
    }

}