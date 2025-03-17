package com.example.hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hospital.entitities.Doctor;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Fetch doctors by specialization
    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByStatus(String status);
    Optional<Doctor> findByEmail(String email);
    List<Doctor> findBySpecializationAndStatus(String specialization, String status);
}
