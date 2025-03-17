package com.example.hospital.service;

import java.util.Collection;
import java.util.List;

import com.example.hospital.entitities.Appointment;

public interface AppointmentService {

	Collection<Appointment> getAppointmnetForUserId(Long uid);

	Collection<Appointment> getAppointmnetForDocId(Long did);

	Collection<Appointment> getAllAppointments();

	Appointment bookAppointment(Appointment appointment);

	Collection<Appointment> getActiveAppointmentsForUser(Long userId);

	void cancelAppointment(Integer appointmentId);

	Appointment getLatestActiveAppointmentForUser(Long userId);

	List<Appointment> getActiveAppointmentsForDoctor(Long doctorId);

	List<Appointment> getCompletedAppointmentsForDoctor(Long doctorId);

	boolean completeAppointment(Integer appointmentId);
}
