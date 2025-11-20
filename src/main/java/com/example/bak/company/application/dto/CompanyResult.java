package com.example.bak.company.application.dto;

import com.example.bak.company.domain.Company;

public record CompanyResult(

) {

    public static CompanyResult from(Company company) {
        return new CompanyResult();
    }
}
