package com.hexaware.controller.dao;

import java.util.List;

import com.hexaware.model.entity.Company;



public interface CompanyDAO {
    void insertCompany(Company company);

    List<Company> getCompanies();

    Company getCompanyById(int companyID);

    
}