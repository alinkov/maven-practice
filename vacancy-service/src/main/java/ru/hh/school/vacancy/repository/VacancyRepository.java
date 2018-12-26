package ru.hh.school.vacancy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.hh.school.vacancy.domain.Vacancy;

public interface VacancyRepository extends
        JpaRepository<Vacancy, Long>, JpaSpecificationExecutor<Vacancy> {
}
