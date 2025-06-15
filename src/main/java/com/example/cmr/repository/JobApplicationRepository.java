package com.example.cmr.repository;

import com.example.cmr.model.Job;
import com.example.cmr.model.JobApplication;
import com.example.cmr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUserId(Long userId);
    boolean existsByUserAndJob(User user, Job job);
}
