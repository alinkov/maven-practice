package ru.hh.school.vacancy.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ru.hh.school.vacancy.domain.Vacancy;
import ru.hh.school.vacancy.repository.VacancyRepository;
import ru.hh.school.vacancy.service.dto.VacancyDTO;
import ru.hh.school.vacancy.service.mapper.VacancyMapper;


@Service
public class VacancyService {
    private final VacancyMapper vacancyMapper;
    private final VacancyRepository vacancyRepository;


    public VacancyService(VacancyMapper vacancyMapper, VacancyRepository vacancyRepository) {
        this.vacancyMapper = vacancyMapper;
        this.vacancyRepository = vacancyRepository;
    }

    public Optional<VacancyDTO> getVacancy(Long id) {
        return vacancyRepository.findById(id)
                .map(vacancyMapper::toDto);
    }

    /**
     * Сохранение вакансии.
     * @param vacancyDto VacancyDTO для сохранения
     * @return VacancyDTO после сохранения
     */
    public VacancyDTO save(VacancyDTO vacancyDto) {
        Vacancy vacancy = vacancyMapper.toEntity(vacancyDto);
        vacancy = vacancyRepository.save(vacancy);
        return vacancyMapper.toDto(vacancy);
    }

    public Page<VacancyDTO> getAllVacancies(Pageable pageable) {
        return vacancyRepository.findAll(pageable)
                .map(vacancyMapper::toDto);
    }
}
