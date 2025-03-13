
package com.example.hospital.entitities;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Specialisation {
		public Specialisation() {
		super();
		// TODO Auto-generated constructor stub
	}

		public Specialisation(Integer id, String specName, Collection<Doctor> doctors) {
		super();
		this.id = id;
		this.specName = specName;
		this.doctors = doctors;
	}

		public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public Collection<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Collection<Doctor> doctors) {
		this.doctors = doctors;
	}

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    private String specName;

	    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL)
	    private Collection<Doctor> doctors = new ArrayList<>();

}
