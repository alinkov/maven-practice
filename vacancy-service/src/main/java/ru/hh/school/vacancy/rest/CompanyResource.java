package ru.hh.school.vacancy.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
import ru.hh.school.vacancy.rest.util.PaginationUtil;
import ru.hh.school.vacancy.rest.util.ResponseUtil;
import ru.hh.school.vacancy.service.CompanyService;
import ru.hh.school.vacancy.service.dto.CompanyDTO;

@RestController
@RequestMapping("/api")
public class CompanyResource {
    private final CompanyService companyService;

    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * GET  /company : Получение всех компаний.
     *
     * @param pageable sort, page и offset
     * @return ResponseEntity со статусом 200 (OK) и списком компаний в теле
     */
    @GetMapping("/company")
    public ResponseEntity<List<CompanyDTO>> getAllSkills(Pageable pageable) {
        Page<CompanyDTO> page = companyService.getAllCompanies(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/company");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /company/:id : Получение компании по id.
     *
     * @param id id компании
     * @return ResponseEntity со статусом 200 (OK) с CompanyDTO в теле, если найдено, либо
     *     пустое тело со статусом 404 (Not Found)
     */
    @GetMapping("/company/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long id) {
        return ResponseUtil.wrapOrNotFound(companyService.getCompany(id));
    }

    /**
     * POST  /company : Создает новый скилл.
     *
     * @param companyDto CompanyDTO для создания
     * @return ResponseEntity со статусом 201 (Created) и CompanyDTO в теле
     * @throws URISyntaxException если неверный URI
     */
    @PostMapping("/company")
    public ResponseEntity<CompanyDTO> saveCompany(@RequestBody CompanyDTO companyDto)
            throws URISyntaxException {
        CompanyDTO result = companyService.save(companyDto);
        return ResponseEntity
                .created(new URI("/api/company/" + result.getId()))
                .body(result);
    }
}
