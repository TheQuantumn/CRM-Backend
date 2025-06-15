package com.example.cmr.repository;

import com.example.cmr.model.Job;
import com.example.cmr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    // For Admin
    List<Job> findAllByOrderByAppliedDateAsc();
    List<Job> findAllByOrderByAppliedDateDesc();

    List<Job> findByStatusOrderByAppliedDateAsc(String status);

    List<Job> findByStatusOrderByAppliedDateDesc(String status);

    // For Applicants
    List<Job> findByUserIdOrderByAppliedDateAsc(Long userId);

    List<Job> findByUserIdOrderByAppliedDateDesc(Long userId);

    List<Job> findByUserIdAndStatusOrderByAppliedDateAsc(Long userId, String status);

    List<Job> findByUserIdAndStatusOrderByAppliedDateDesc(Long userId, String status);
}

