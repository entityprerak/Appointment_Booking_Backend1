package com.example.hospital.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hospital.entitities.Appointment;
import com.example.hospital.entitities.Slot;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

	Collection<Appointment> findByUserId(Long uid);

	List<Appointment> findByDoctorId(Long did);
	List<Appointment> findByDoctorIdAndAppointmentStatus(Long doctorId, String status);
	Collection<Appointment> findByUserIdAndAppointmentStatus(Long userId, String status);
	Appointment findTopByUserIdAndAppointmentStatusOrderByDateDesc(Long userId, String status);
	
	

	@Query("SELECT a FROM Appointment a WHERE a.doctor.id = ?1 AND a.slot.date = ?2")
	Collection<Appointment> getAppointmentByDoctorIdAndDate(Long doctorId,
			LocalDate date);

	long countBySlot(Slot slot);

}