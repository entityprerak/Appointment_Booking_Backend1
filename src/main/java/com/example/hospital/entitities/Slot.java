package com.example.hospital.entitities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    // ✅ Fixed: Many-to-One Relationship with Doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // ✅ Fixed: One-to-Many Relationship with Appointments
    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    // ✅ Default Constructor
    public Slot() {}

    // ✅ Constructor with Doctor Relationship
    public Slot(int id, LocalDate date, LocalTime startTime, LocalTime endTime, Doctor doctor) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
    }

    // ✅ Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
}
