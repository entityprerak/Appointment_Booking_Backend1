package com.example.hospital.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hospital.entitities.Appointment;
import com.example.hospital.entitities.Doctor;
import com.example.hospital.Repository.AppointmentRepository;
import com.example.hospital.Repository.DoctorRepository;
import com.example.hospital.service.DoctorService;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;;

    @Override
    public Doctor registerDoctor(Doctor doctor) {
        doctor.setStatus("PENDING"); // Default status
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getPendingDoctors() {
        return doctorRepository.findByStatus("PENDING");
    }

    @Override
    public Doctor approveDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setStatus("APPROVED");
        return doctorRepository.save(doctor);
    }

    @Override
    public void rejectDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    @Override
    public Doctor updateDoctor(Long doctorId, Doctor updatedDoctor) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        doctor.setDoctorname(updatedDoctor.getDoctorname());
        doctor.setSpecialization(updatedDoctor.getSpecialization());
        doctor.setMobile(updatedDoctor.getMobile());

        return doctorRepository.save(doctor);
    }

    @Override
    public void deleteDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    @Override
    public List<Doctor> getApprovedDoctors() {
        return doctorRepository.findByStatus("APPROVED");
    }
    @Override
    public List<Appointment> getDoctorAppointments(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    @Override
    public Appointment completeAppointment(Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // ✅ Mark the appointment as COMPLETED
        appointment.setAppointmentStatus("COMPLETED");
        return appointmentRepository.save(appointment);
    }
}
