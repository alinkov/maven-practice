package ru.hh.school.vacancy.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.hh.school.vacancy.domain.Skill;
import ru.hh.school.vacancy.repository.SkillRepository;
import ru.hh.school.vacancy.service.dto.SkillDTO;
import ru.hh.school.vacancy.service.mapper.SkillMapper;


@Service
public class SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public SkillService(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Transactional(readOnly = true)
    public Optional<SkillDTO> getSkill(Long id) {
        return skillRepository.findById(id).map(skillMapper::toDto);
    }

    /**
     * Сохранение скилла.
     * @param skillDto SkillDTO для сохранения
     * @return SkillDTO после сохранения
     */
    @Transactional
    public SkillDTO save(SkillDTO skillDto) {
        Skill skill = skillMapper.toEntity(skillDto);
        skill = skillRepository.save(skill);
        return skillMapper.toDto(skill);
    }

    public Page<SkillDTO> getAllSkills(Pageable pageable) {
        return skillRepository.findAll(pageable)
                .map(skillMapper::toDto);
    }
}
