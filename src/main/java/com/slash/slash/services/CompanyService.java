package com.slash.slash.services;

import com.slash.slash.models.Company;

import java.util.List;

public interface CompanyService {

    public Company addCompany (Company company);
    public void deleteCompany (Company company);
    public Company editCompany (Company oldCompany, Company newCompany);
    public List<Company> listCompanies();
    public Company retrieveCompanyByName(String name);
}
