package com.hexaware.controller.dao;

import java.util.List;

import com.hexaware.model.entity.Applicant;



public interface ApplicantDAO {
    void insertApplicant(Applicant applicant);

    List<Applicant> getApplicants();

    Applicant getApplicantById(int applicantID);

    
}
