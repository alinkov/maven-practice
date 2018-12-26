package ru.hh.school.vacancy.rest;

import javax.persistence.EntityManager;

import ru.hh.school.vacancy.domain.Company;

public class CompanyResourceIntTest {
    private static final String DEFAULT_TITLE = "company";
    private static final String DEFAULT_DESCRIPTION = "description";
    private static final String DEFAULT_CONTACTS = "contacts";

    public static Company createEntity(EntityManager em) {
        Company company = Company.builder()
                .title(DEFAULT_TITLE)
                .description(DEFAULT_DESCRIPTION)
                .contacts(DEFAULT_CONTACTS)
                .build();
        return company;
    }
}
