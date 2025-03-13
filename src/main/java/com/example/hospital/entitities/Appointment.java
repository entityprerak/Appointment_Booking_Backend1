package com.example.hospital.entitities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Appointment {
	 	

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private LocalDate date;
	    private String problem;
	    private String appointmentStatus;
	    public Appointment() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Appointment(int id, LocalDate date, String problem, String appointmentStatus, LocalTime startTime,
				LocalTime endTime, Doctor doctor, Slot slot, User user) {
			super();
			this.id = id;
			this.date = date;
			this.problem = problem;
			this.appointmentStatus = appointmentStatus;
			this.startTime = startTime;
			this.endTime = endTime;
			this.doctor = doctor;
			this.slot = slot;
			this.user = user;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public LocalDate getDate() {
			return date;
		}

		public void setDate(LocalDate date) {
			this.date = date;
		}

		public String getProblem() {
			return problem;
		}

		public void setProblem(String problem) {
			this.problem = problem;
		}

		public String getAppointmentStatus() {
			return appointmentStatus;
		}

		public void setAppointmentStatus(String appointmentStatus) {
			this.appointmentStatus = appointmentStatus;
		}

		public LocalTime getStartTime() {
			return startTime;
		}

		public void setStartTime(LocalTime startTime) {
			this.startTime = startTime;
		}

		public LocalTime getEndTime() {
			return endTime;
		}

		public void setEndTime(LocalTime endTime) {
			this.endTime = endTime;
		}

		public Doctor getDoctor() {
			return doctor;
		}

		public void setDoctor(Doctor doctor) {
			this.doctor = doctor;
		}

		public Slot getSlot() {
			return slot;
		}

		public void setSlot(Slot slot) {
			this.slot = slot;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		private LocalTime startTime;
	    private LocalTime endTime;

	    @ManyToOne
	    @JoinColumn(name = "doctor_id", nullable = false)
	    private Doctor doctor;

	   @ManyToOne
	   @JoinColumn(name = "slot_id", nullable = false)
	   private Slot slot;

	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;
}
