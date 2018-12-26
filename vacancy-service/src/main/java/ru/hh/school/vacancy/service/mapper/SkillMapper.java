package ru.hh.school.vacancy.service.mapper;

import org.springframework.stereotype.Component;
import ru.hh.school.vacancy.domain.Skill;
import ru.hh.school.vacancy.service.dto.SkillDTO;

@Component
public class SkillMapper implements Mapper<Skill, SkillDTO> {
    @Override
    public SkillDTO toDto(Skill skill) {
        if (skill == null) {
            throw new IllegalArgumentException("skill should be not null");
        }
        return SkillDTO.builder()
                .id(skill.getId())
                .title(skill.getTitle())
                .build();
    }

    @Override
    public Skill toEntity(SkillDTO skillDTO) {
        if (skillDTO == null) {
            throw new IllegalArgumentException("skillDTO should be not null");
        }
        return Skill.builder()
                .id(skillDTO.getId())
                .title(skillDTO.getTitle())
                .build();
    }

}
