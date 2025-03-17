package com.example.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.hospital.entitities.Doctor;
import com.example.hospital.entitities.User;
import com.example.hospital.service.AdminService;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ✅ View All Users
    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    // ✅ View All Doctors
    @GetMapping("/allDoctors")
    public List<Doctor> getAllDoctors() {
        return adminService.getAllDoctors();
    }

    // ✅ View Pending Doctors
    @GetMapping("/pendingDoctors")
    public List<Doctor> getPendingDoctors() {
        return adminService.getPendingDoctors();
    }

    // ✅ Approve Doctor
    @PutMapping("/approveDoctor/{doctorId}")
    public String approveDoctor(@PathVariable Long doctorId) {
        adminService.approveDoctor(doctorId);
        return "Doctor approved successfully.";
    }

    // ✅ Reject Doctor
    @DeleteMapping("/rejectDoctor/{doctorId}")
    public String rejectDoctor(@PathVariable Long doctorId) {
        adminService.rejectDoctor(doctorId);
        return "Doctor registration rejected.";
    }

    // ✅ Delete Any User
    @DeleteMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return "User deleted successfully.";
    }

    // ✅ Delete Any Doctor
    @DeleteMapping("/deleteDoctor/{doctorId}")
    public String deleteDoctor(@PathVariable Long doctorId) {
        adminService.deleteDoctor(doctorId);
        return "Doctor deleted successfully.";
    }
}
