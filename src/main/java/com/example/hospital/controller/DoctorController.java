package com.example.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hospital.Repository.DoctorRepository;
import com.example.hospital.entitities.Doctor;
import com.example.hospital.entitities.Slot;
import com.example.hospital.service.DoctorService;
import com.example.hospital.service.SlotService;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private SlotService slotService;


    // Fetch doctors based on optional specialization
    @GetMapping
    public List<Doctor> getDoctors(@RequestParam(value = "speciality", required = false) String speciality) {
        if (speciality == null || speciality.isEmpty()) {
            // If no specialization is provided, return all doctors
        	return doctorRepository.findByStatus("APPROVED");
        }
        // Return doctors filtered by specialization
        return doctorRepository.findBySpecializationAndStatus(speciality, "APPROVED");

    }

    // Register a new doctor with default "PENDING" status
    @PostMapping("/register")
    public ResponseEntity<String> registerDoctor(@RequestBody Doctor doctor) {
        doctor.setStatus("PENDING");
        doctorRepository.save(doctor);
        return ResponseEntity.ok("Doctor registration request submitted. Awaiting admin approval.");
    }

    // Retrieve all pending doctor registrations
    @GetMapping("/pending")
    public List<Doctor> getPendingDoctors() {
        return doctorRepository.findByStatus("PENDING");
    }

    // Approve a doctor by updating their status to "APPROVED"
    @PutMapping("/update/{doctorId}")
    public Doctor updateDoctor(@PathVariable Long doctorId, @RequestBody Doctor updatedDoctor) {
        return doctorService.updateDoctor(doctorId, updatedDoctor);
    }

    @DeleteMapping("/delete/{doctorId}")
    public void deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
    }
    // ✅ Allow doctor to add slots
    @PostMapping("/addSlot/{doctorId}")
    public Slot addSlot(@PathVariable Long doctorId, @RequestBody Slot slot) {
        return slotService.addDoctorSlot(doctorId, slot);
    }

    // ✅ Fetch all slots for a doctor
    @GetMapping("/slots/{doctorId}")
    public List<Slot> getDoctorSlots(@PathVariable Long doctorId) {
        return slotService.getDoctorSlots(doctorId);
    }
}
