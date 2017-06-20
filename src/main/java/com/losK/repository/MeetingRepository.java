package com.losK.repository;

import com.losK.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by m.losK on 2017-04-23.
 */
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}