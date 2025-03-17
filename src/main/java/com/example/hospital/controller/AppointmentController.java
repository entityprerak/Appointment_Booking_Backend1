package com.example.hospital.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital.entitities.Appointment;
import com.example.hospital.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@GetMapping("/user")
	private Collection<Appointment> getAppointmentsByUId(@RequestHeader("userId") Long uid){
		return this.appointmentService.getAppointmnetForUserId(uid);
	}
	
	@GetMapping("/doctor/{doctorid}")
	private Collection<Appointment> getAppointmentsByDId(@PathVariable("doctorid") Long did) {
		return this.appointmentService.getAppointmnetForDocId(did);
	}
	
	@GetMapping("/all")
    public Collection<Appointment> getAllAppointments() {
        return this.appointmentService.getAllAppointments();
    }
	
	@PostMapping("/new")
    public Appointment bookAppointment(@RequestBody Appointment appointment) {
        return this.appointmentService.bookAppointment(appointment);
    }
	
	
	
	@DeleteMapping("/{appointmentId}")
    public void cancelAppointment(@PathVariable Integer appointmentId) {
        this.appointmentService.cancelAppointment(appointmentId);
        return;
    }
	
	@GetMapping("/active")
	public ResponseEntity<?> getActiveAppointments(@RequestHeader("userId") Long userId) {
	    Collection<Appointment> activeAppointments = appointmentService.getActiveAppointmentsForUser(userId);

	    if (activeAppointments.isEmpty()) {
	        return ResponseEntity.ok("No Appointments Found");
	    }

	    return ResponseEntity.ok(activeAppointments);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> cancelLatestUserAppointment(@RequestHeader("userId") Long userId) {
	    // Fetch the latest active appointment for the user
	    Appointment latestAppointment = appointmentService.getLatestActiveAppointmentForUser(userId);
	
	    if (latestAppointment == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active appointments found for deletion.");
	    }
	
	    // Delete the appointment
	    appointmentService.cancelAppointment(latestAppointment.getId());
	    return ResponseEntity.ok("Latest active appointment successfully deleted.");
	}
	
	// Get all active appointments for a doctor
	// Get all active appointments for a doctor
	@GetMapping("/doctor/{doctorId}/active")
	public ResponseEntity<List<Appointment>> getActiveAppointmentsForDoctor(@PathVariable Long doctorId) {
	    List<Appointment> activeAppointments = appointmentService.getActiveAppointmentsForDoctor(doctorId);
	    return ResponseEntity.ok(activeAppointments);
	}

	// Get all completed appointments for a doctor
	@GetMapping("/doctor/{doctorId}/completed")
	public ResponseEntity<List<Appointment>> getCompletedAppointmentsForDoctor(@PathVariable Long doctorId) {
	    List<Appointment> completedAppointments = appointmentService.getCompletedAppointmentsForDoctor(doctorId);
	    return ResponseEntity.ok(completedAppointments);
	}

	
}
