package com.example.hospital.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hospital.entitities.Doctor;
import com.example.hospital.entitities.User;
import com.example.hospital.Repository.DoctorRepository;
import com.example.hospital.Repository.UserRepository;
import com.example.hospital.service.AdminService;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Doctor> getPendingDoctors() {
        return doctorRepository.findByStatus("PENDING");
    }

    @Override
    public void approveDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setStatus("APPROVED");
        doctorRepository.save(doctor);
    }

    @Override
    public void rejectDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }
}
