package com.example.cmr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    private String password;

    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Job> jobs;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<JobApplication> applications;

    // You already have these, but they are redundant with @Data unless you plan to override them
    // Remove them if not needed, else keep as-is

    public void setUsername(String username) { this.username = username; }

    public void setId(Long id) { this.id = id; }

    public void setPassword(String password) { this.password = password; }

    public void setJobs(List<Job> jobs) { this.jobs = jobs; }

    public void setApplications(List<JobApplication> applications) { this.applications = applications; }

    public void setRole(String role) { this.role = role; }
}
