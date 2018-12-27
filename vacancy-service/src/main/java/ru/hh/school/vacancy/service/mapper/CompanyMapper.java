package ru.hh.school.vacancy.service.mapper;

import org.springframework.stereotype.Component;
import ru.hh.school.vacancy.domain.Company;
import ru.hh.school.vacancy.service.dto.CompanyDTO;

@Component
public class CompanyMapper implements Mapper<Company, CompanyDTO> {
    @Override
    public CompanyDTO toDto(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("company should not be null");
        }
        return CompanyDTO.builder()
                .id(company.getId())
                .title(company.getTitle())
                .description(company.getDescription())
                .contacts(company.getContacts())
                .build();
    }

    @Override
    public Company toEntity(CompanyDTO companyDTO) {
        if (companyDTO == null) {
            throw new IllegalArgumentException("companyDTO should not be null");
        }
        return Company.builder()
                .id(companyDTO.getId())
                .title(companyDTO.getTitle())
                .description(companyDTO.getDescription())
                .contacts(companyDTO.getContacts())
                .build();
    }
}
