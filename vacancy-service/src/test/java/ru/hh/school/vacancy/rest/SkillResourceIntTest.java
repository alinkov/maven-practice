package ru.hh.school.vacancy.rest;

import ru.hh.school.vacancy.domain.Skill;

import javax.persistence.EntityManager;

public class SkillResourceIntTest {
    private static final String DEFAULT_TITLE = "skill";

    public static Skill createEntity(EntityManager em) {
        Skill vacancy = Skill.builder()
                .title(DEFAULT_TITLE)
                .build();
        return vacancy;
    }
}
