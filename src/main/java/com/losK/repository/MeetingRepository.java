package com.losK.repository;

import com.losK.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by m.losK on 2017-04-23.
 */
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByCaptionStartsWithIgnoreCase(String caption);
}