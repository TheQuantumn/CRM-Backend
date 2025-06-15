package com.example.cmr.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship to User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Relationship to Job
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    // Status of the application
    private String status = "Applied";  // Could be Applied, Interviewing, Offered, Rejected, etc.

    // Date the user applied
    private LocalDate appliedDate = LocalDate.now();

    // Optional notes by user
    private String notes;
}
