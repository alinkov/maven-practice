package ru.hh.school.vacancy.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ru.hh.school.vacancy.domain.Company;
import ru.hh.school.vacancy.domain.Skill;
import ru.hh.school.vacancy.domain.Vacancy;
import ru.hh.school.vacancy.service.dto.CompanyDTO;
import ru.hh.school.vacancy.service.dto.SkillDTO;
import ru.hh.school.vacancy.service.dto.VacancyDTO;


@Component
public class VacancyMapper implements Mapper<Vacancy, VacancyDTO> {
    private final CompanyMapper companyMapper;
    private final SkillMapper skillMapper;

    public VacancyMapper(CompanyMapper companyMapper, SkillMapper skillMapper) {
        this.companyMapper = companyMapper;
        this.skillMapper = skillMapper;
    }

    @Override
    public Vacancy toEntity(VacancyDTO vacancyDTO) {
        if (vacancyDTO == null) {
            throw new IllegalArgumentException("vacancyDTO should be not null");
        }
        Company company = Optional.ofNullable(vacancyDTO.getCompany())
                .map(companyMapper::toEntity)
                .orElse(null);

        List<Skill> skillList = Optional.ofNullable(vacancyDTO.getSkills())
                .map(skills -> skills.stream()
                        .map(skillMapper::toEntity)
                        .collect(Collectors.toList()))
                .orElseGet(ArrayList::new);

        return Vacancy.builder()
                .id(vacancyDTO.getId())
                .position(vacancyDTO.getPosition())
                .description(vacancyDTO.getDescription())
                .salaryFrom(vacancyDTO.getSalaryFrom())
                .salaryTo(vacancyDTO.getSalaryTo())
                .skills(skillList)
                .company(company)
                .build();
    }

    @Override
    public VacancyDTO toDto(Vacancy vacancy) {
        if (vacancy == null) {
            throw new IllegalArgumentException("vacancy should be not null");
        }
        CompanyDTO company = Optional.ofNullable(vacancy.getCompany())
                .map(companyMapper::toDto)
                .orElse(null);

        List<SkillDTO> skillList = Optional.ofNullable(vacancy.getSkills())
                .map(skills -> skills.stream().map(skillMapper::toDto).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);

        return VacancyDTO.builder()
                .id(vacancy.getId())
                .position(vacancy.getPosition())
                .description(vacancy.getDescription())
                .salaryFrom(vacancy.getSalaryFrom())
                .salaryTo(vacancy.getSalaryTo())
                .skills(skillList)
                .company(company)
                .build();
    }
}
