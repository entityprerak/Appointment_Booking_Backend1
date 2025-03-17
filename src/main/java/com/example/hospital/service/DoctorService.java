package com.example.hospital.service;

import com.example.hospital.entitities.Appointment;
import com.example.hospital.entitities.Doctor;
import java.util.List;

public interface DoctorService {
    Doctor registerDoctor(Doctor doctor);
    List<Doctor> getPendingDoctors();
    Doctor approveDoctor(Long doctorId);
    void rejectDoctor(Long doctorId);
    Doctor updateDoctor(Long doctorId, Doctor updatedDoctor);
    void deleteDoctor(Long doctorId);
    List<Doctor> getApprovedDoctors();
    Appointment completeAppointment(Integer appointmentId);
    List<Appointment> getDoctorAppointments(Long doctorId);
}
