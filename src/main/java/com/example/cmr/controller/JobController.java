package com.example.cmr.controller;

import com.example.cmr.model.Job;
import com.example.cmr.model.User;
import com.example.cmr.repository.JobRepository;
import com.example.cmr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @GetMapping("/test")
    public ResponseEntity<String> testSecured() {
        return ResponseEntity.ok("You are authenticated and can access this endpoint!");
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        job.setUser(user);  // Associate job with current user
        System.out.println("Authenticated user: " + authentication.getName());
        return ResponseEntity.ok(jobRepository.save(job));
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs(
            @RequestParam(required = false) String status,
            @RequestParam(required = false, defaultValue = "asc") String sort) {

        List<Job> jobs;

        if (status != null) {
            jobs = sort.equalsIgnoreCase("desc")
                    ? jobRepository.findByStatusOrderByAppliedDateDesc(status)
                    : jobRepository.findByStatusOrderByAppliedDateAsc(status);
        } else {
            jobs = sort.equalsIgnoreCase("desc")
                    ? jobRepository.findAllByOrderByAppliedDateDesc()
                    : jobRepository.findAllByOrderByAppliedDateAsc();
        }

        return ResponseEntity.ok(jobs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id, @RequestBody Job jobRequest, Authentication authentication) {
        Job job = jobRepository.findById(id).orElseThrow();
        if (!job.getUser().getUsername().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not your job to edit");
        }

        job.setCompany(jobRequest.getCompany());
        job.setRole(jobRequest.getRole());
        job.setStatus(jobRequest.getStatus());
        job.setAppliedDate(jobRequest.getAppliedDate());
        job.setNotes(jobRequest.getNotes());
        job.setDescription(jobRequest.getDescription()); // ✅ Added line

        return ResponseEntity.ok(jobRepository.save(job));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id, Authentication authentication) {
        Job job = jobRepository.findById(id).orElseThrow();
        if (!job.getUser().getUsername().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not your job to delete");
        }
        jobRepository.delete(job);
        return ResponseEntity.ok("Job deleted");
    }
}
