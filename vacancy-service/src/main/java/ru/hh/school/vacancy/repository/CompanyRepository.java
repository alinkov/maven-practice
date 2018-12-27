package ru.hh.school.vacancy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hh.school.vacancy.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
