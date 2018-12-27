package ru.hh.school.vacancy.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.vacancy.domain.Company;
import ru.hh.school.vacancy.repository.CompanyRepository;
import ru.hh.school.vacancy.service.dto.CompanyDTO;
import ru.hh.school.vacancy.service.mapper.CompanyMapper;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Transactional(readOnly = true)
    public Optional<CompanyDTO> getCompany(Long id) {
        return companyRepository.findById(id)
                .map(companyMapper::toDto);
    }

    /**
     * Сохранение компании.
     * @param companyDto CompanyDTO для сохранения
     * @return CompanyDTO после сохранения
     */
    @Transactional
    public CompanyDTO save(CompanyDTO companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    public Page<CompanyDTO> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .map(companyMapper::toDto);
    }
}
