package com.slash.slash.services;

import com.slash.slash.models.Company;
import com.slash.slash.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company addCompany(Company company) {

        List<Company> companyList = listCompanies();
        for (Company savedCompany : companyList) {
            if (savedCompany.getName() == company.getName()) {
                System.out.println("Company name already exists");
                return null;
            }
        }

        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Company company) {
        if (company != null) {
            companyRepository.delete(company);
        } else {
            System.out.println("Company does not exist");
        }
    }

    @Override
    public Company editCompany(Company oldCompany, Company newCompany) {
        oldCompany.setAddress(newCompany.getAddress());
        oldCompany.setCity(newCompany.getCity());
        oldCompany.setName(newCompany.getName());
        oldCompany.setPhoneNumber(newCompany.getPhoneNumber());

        return oldCompany;
    }

    @Override
    public List<Company> listCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company retrieveCompanyByName(String name) {
        List<Company> companyList = listCompanies();

        for (Company company : companyList) {
            if (company.getName() == name) return company;
        }
        return null;
    }
}
