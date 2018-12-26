package ru.hh.school.vacancy.rest;

import javax.persistence.EntityManager;

import ru.hh.school.vacancy.domain.Skill;

public class SkillResourceIntTest {
    private static final String DEFAULT_TITLE = "skill";

    public static Skill createEntity(EntityManager em) {
        return Skill.builder()
                .title(DEFAULT_TITLE)
                .build();
    }
}
