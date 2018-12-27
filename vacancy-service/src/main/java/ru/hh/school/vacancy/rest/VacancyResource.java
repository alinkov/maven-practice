package ru.hh.school.vacancy.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hh.school.vacancy.domain.Vacancy;
import ru.hh.school.vacancy.rest.util.PaginationUtil;
import ru.hh.school.vacancy.rest.util.ResponseUtil;
import ru.hh.school.vacancy.service.VacancyService;
import ru.hh.school.vacancy.service.dto.VacancyDTO;

@RestController
@RequestMapping("/api")
public class VacancyResource {
    private final VacancyService vacancyService;

    public VacancyResource(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    /**
     * GET  /vacancy : Получение всех вакансий.
     *
     * @param pageable sort, page и offset
     * @return ResponseEntity со статусом 200 (OK) и списком вакансий в теле
     */
    @GetMapping("/vacancy")
    public ResponseEntity<List<VacancyDTO>> getAllSkills(Pageable pageable) {
        Page<VacancyDTO> page = vacancyService.getAllVacancies(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vacancy");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vacancy/:id : Получение вакансии по id.
     *
     * @param id id вакансии
     * @return ResponseEntity со статусом 200 (OK) с VacancyDTO в теле, если найдено, либо
     *     пустое тело со статусом 404 (Not Found)
     */
    @GetMapping("/vacancy/{id}")
    public ResponseEntity<VacancyDTO> getVacancy(@PathVariable Long id) {
        Optional<VacancyDTO> vacancy = vacancyService.getVacancy(id);
        return ResponseUtil.wrapOrNotFound(vacancy);
    }

    /**
     * POST  /vacancy : Создает новый скилл.
     *
     * @param vacancyDto VacancyDTO для создания
     * @return ResponseEntity со статусом 201 (Created) и VacancyDTO в теле
     * @throws URISyntaxException если неверный URI
     */
    @PostMapping("/vacancy")
    public ResponseEntity<VacancyDTO> save(@RequestBody VacancyDTO vacancyDto)
            throws URISyntaxException {
        VacancyDTO result = vacancyService.save(vacancyDto);
        return ResponseEntity
                .created(new URI("/api/vacancy/" + result.getId()))
                .body(result);
    }
}
