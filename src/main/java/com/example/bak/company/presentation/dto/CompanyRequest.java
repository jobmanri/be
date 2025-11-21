package com.example.bak.company.presentation.dto;

public record CompanyRequest(
        String name,
        String careerLink,
        String logoUrl,
        String description
) {
    
}
