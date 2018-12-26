package ru.hh.school.vacancy.service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VacancyDTO {
    private Long id;
    private String position;
    private CompanyDTO company;
    private Long salaryFrom;
    private Long salaryTo;
    private String description;
    private List<SkillDTO> skills;
}
