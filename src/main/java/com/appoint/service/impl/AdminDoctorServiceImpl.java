package com.appoint.service.impl;

import java.util.List;
import java.util.Optional;

import com.appoint.service.AdminDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appoint.entity.Doctor;
import com.appoint.exception.DoctorException;
import com.appoint.repository.AppointmentRepository;
import com.appoint.repository.DoctorRepository;

@Service
public class AdminDoctorServiceImpl implements AdminDoctorService {
	
	@Autowired
    DoctorRepository doctorRepository;
	@Autowired
	AppointmentRepository appointmentRepository;

	@Override
	public Doctor registerDoctor(Doctor doctor) throws DoctorException {
		Doctor databaseDoctor = doctorRepository.findByMobileNo(doctor.getMobileNo());
		if(databaseDoctor == null) {
			doctor.setType("Doctor");
			doctor.setPassword(PatientServiceImpl.bCryptPasswordEncoder.encode(doctor.getPassword()));
			return doctorRepository.save(doctor);
		}else {
			throw new DoctorException("Doctor already register with is mobile no. " + doctor.getMobileNo());
		}
	}

	@Override
	public Doctor revokePermissionOfDoctor(Doctor doctor) throws DoctorException {
		Optional<Doctor> registerDoctor = doctorRepository.findById(doctor.getDoctorId());
		if(registerDoctor.isPresent()) {
			registerDoctor.get().setValidDoctor(false);
			return doctorRepository.save(registerDoctor.get());
		}else {
			throw new DoctorException("Doctor not present with this id " + doctor.getDoctorId());
		}
	}
	
	@Override
	public Doctor grantPermissionOfDoctor(Doctor doctor) throws DoctorException {
		Optional<Doctor> registerDoctor = doctorRepository.findById(doctor.getDoctorId());
		if(registerDoctor.isPresent()) {
			registerDoctor.get().setValidDoctor(true);
			return doctorRepository.save(registerDoctor.get());
		}else {
			throw new DoctorException("Doctor not present with this id " + doctor.getDoctorId());
		}
	}

	@Override
	public List<Doctor> getAllValidInValidDoctors(String key) throws DoctorException {
		List<Doctor> listOfDoctors = doctorRepository.findAll();
		if(!listOfDoctors.isEmpty()) {
			return listOfDoctors;
		}else {
			throw new DoctorException("No doctors register. Please contact admin.");
		}
	}

}
