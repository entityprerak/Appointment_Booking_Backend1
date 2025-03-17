package com.example.hospital.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hospital.entitities.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {
    
    // Fetch all slots for a doctor on a specific date
    @Query("SELECT s FROM Slot s WHERE s.date = :date AND s.doctor.id = :doctorId")
    Collection<Slot> findSlotsByDoctorAndDate(Long doctorId, LocalDate date);

    // Fetch slots that have less than 4 appointments
    @Query("SELECT s FROM Slot s WHERE s.date = :date AND s.doctor.id = :doctorId " +
           "AND (SELECT COUNT(a) FROM Appointment a WHERE a.slot = s) < 4")
    Collection<Slot> findAvailableSlots(Long doctorId, LocalDate date);
    
    List<Slot> findByDoctorId(Long doctorId);
}