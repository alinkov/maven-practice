package ru.hh.school.vacancy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hh.school.vacancy.domain.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
