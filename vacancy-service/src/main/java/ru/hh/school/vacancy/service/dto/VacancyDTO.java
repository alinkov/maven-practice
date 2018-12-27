package ru.hh.school.vacancy.service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyDTO {
    private Long id;
    private String position;
    private CompanyDTO company;
    private Long salaryFrom;
    private Long salaryTo;
    private String description;
    @EqualsAndHashCode.Exclude
    private List<SkillDTO> skills;
}
