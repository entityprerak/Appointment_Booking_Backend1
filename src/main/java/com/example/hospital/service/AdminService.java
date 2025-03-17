package com.example.hospital.service;

import com.example.hospital.entitities.Doctor;
import com.example.hospital.entitities.User;
import java.util.List;

public interface AdminService {
    List<User> getAllUsers();
    List<Doctor> getAllDoctors();
    List<Doctor> getPendingDoctors();
    void approveDoctor(Long doctorId);
    void rejectDoctor(Long doctorId);
    void deleteUser(Long userId);
    void deleteDoctor(Long doctorId);
}
