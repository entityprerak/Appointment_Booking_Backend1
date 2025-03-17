package com.example.hospital.entitities;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String doctorname;
    private String mobile;
    private String gender;
    private String experience;
    private String specialization;
    private String address;
    
    @Column(nullable = false)
    private String status = "PENDING"; // Default status for new doctors

    // ✅ Fixed: Corrected One-to-Many Mapping for Slots
    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Slot> slots;

    // ✅ Fixed: Appointments Relationship is now Correctly Mapped
    @JsonIgnore
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    public Doctor() {}

    public Doctor(Long id, String email, String password, String doctorname, String mobile, String gender, String experience,
                  String specialization, String address, String status, List<Slot> slots, List<Appointment> appointments) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.doctorname = doctorname;
        this.mobile = mobile;
        this.gender = gender;
        this.experience = experience;
        this.specialization = specialization;
        this.address = address;
        this.status = status;
        this.slots = slots;
        this.appointments = appointments;
    }

    // ✅ Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDoctorname() { return doctorname; }
    public void setDoctorname(String doctorname) { this.doctorname = doctorname; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Slot> getSlots() { return slots; }
    public void setSlots(List<Slot> slots) { this.slots = slots; }

    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
}
