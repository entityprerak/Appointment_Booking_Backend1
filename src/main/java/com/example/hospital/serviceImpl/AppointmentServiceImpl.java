package com.example.hospital.serviceImpl;


import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.hospital.entitities.Appointment;
import com.example.hospital.entitities.Doctor;
import com.example.hospital.entitities.Slot;
import com.example.hospital.entitities.User;
import com.example.hospital.service.AppointmentService;
import com.example.hospital.Repository.AppointmentRepository;
import com.example.hospital.Repository.DoctorRepository;
import com.example.hospital.Repository.SlotRepository;
import com.example.hospital.Repository.UserRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService{

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SlotRepository slotRepository;
	
	@Override
	public Collection<Appointment> getAppointmnetForUserId(Long uid) {
		return this.appointmentRepository.findByUserId(uid);
	}

	@Override
	public Collection<Appointment> getAppointmnetForDocId(Long did) {
		return this.appointmentRepository.findByDoctorId(did);
	}

	@Override
	public Collection<Appointment> getAllAppointments() {
		return this.appointmentRepository.findAll();
	}

	@Override
	public Appointment bookAppointment(Appointment appointment) {
	
	    Doctor doctor = this.doctorRepository.findById(appointment.getDoctor().getId())
	        .orElseThrow(() -> new RuntimeException("Doctor not found"));

	    // Fetch the slot from DB
	    Slot slot = slotRepository.findById(appointment.getSlot().getId())
	        .orElseThrow(() -> new RuntimeException("Slot not found"));

	    // Fetch the user from DB
	    User user = userRepository.findById(appointment.getUser().getId())
	        .orElseThrow(() -> new RuntimeException("User not found"));

	    // Check if slot is available (assuming max 4 appointments per slot)
	    long existingAppointments = appointmentRepository.countBySlot(slot);
	    if (existingAppointments >= 4) {
	        throw new RuntimeException("Slot is already fully booked");
	    }

	    // Create a new appointment with actual database entities
	    Appointment newAppointment = new Appointment();
	    newAppointment.setDate(appointment.getDate());
	    newAppointment.setAppointmentStatus("Upcoming");
	    newAppointment.setDoctor(doctor);
	    newAppointment.setProblem(appointment.getProblem());
	    newAppointment.setSlot(slot);
	    newAppointment.setUser(user);
		
		return this.appointmentRepository.save(newAppointment);
	}



	@Override
	public void cancelAppointment(Integer appointmentId) {
		this.appointmentRepository.deleteById(appointmentId);
	}
	@Override
	public List<Appointment> getActiveAppointmentsForDoctor(Long doctorId) {
	    return appointmentRepository.findByDoctorIdAndAppointmentStatus(doctorId, "Active");
	}

	@Override
	public List<Appointment> getCompletedAppointmentsForDoctor(Long doctorId) {
	    return appointmentRepository.findByDoctorIdAndAppointmentStatus(doctorId, "COMPLETED");
	}
	@Override
	public boolean completeAppointment(Integer appointmentId) {
	    Appointment appointment = appointmentRepository.findById(appointmentId)
	            .orElseThrow(() -> new RuntimeException("Appointment not found"));

	    if ("COMPLETED".equals(appointment.getAppointmentStatus())) {
	        return false; // Already completed
	    }

	    appointment.setAppointmentStatus("COMPLETED");
	    appointmentRepository.save(appointment);
	    return true;
	}

	@Override
	public Collection<Appointment> getActiveAppointmentsForUser(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Appointment getLatestActiveAppointmentForUser(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}



	

	
}
