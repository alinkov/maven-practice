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

import ru.hh.school.vacancy.rest.util.PaginationUtil;
import ru.hh.school.vacancy.rest.util.ResponseUtil;
import ru.hh.school.vacancy.service.SkillService;
import ru.hh.school.vacancy.service.dto.SkillDTO;


@RestController
@RequestMapping("/api")
public class SkillResource {
    private final SkillService skillService;

    public SkillResource(SkillService skillService) {
        this.skillService = skillService;
    }

    /**
     * GET  /skill : Получение всех скиллов.
     *
     * @param pageable sort, page и offset
     * @return ResponseEntity со статусом 200 (OK) и списком скиллов в теле
     */
    @GetMapping("/skill")
    public ResponseEntity<List<SkillDTO>> getAllSkills(Pageable pageable) {
        Page<SkillDTO> page = skillService.getAllSkills(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/skill");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /skill/:id : Получение скилла по id.
     *
     * @param id id скилла
     * @return ResponseEntity со статусом 200 (OK) с SkillDTO в теле, если найдено, либо
     *     пустое тело со статусом 404 (Not Found)
     */
    @GetMapping("/skill/{id}")
    public ResponseEntity<SkillDTO> getSkill(@PathVariable Long id) {
        Optional<SkillDTO> skill = skillService.getSkill(id);
        return ResponseUtil.wrapOrNotFound(skill);
    }

    /**
     * POST  /skill : Создает новый скилл.
     *
     * @param skillDto SkillDTO для создания
     * @return ResponseEntity со статусом 201 (Created) и SkillDTO в теле
     * @throws URISyntaxException если неверный URI
     */
    @PostMapping("/skill")
    public ResponseEntity<SkillDTO> save(@RequestBody SkillDTO skillDto)
            throws URISyntaxException {
        SkillDTO result = skillService.save(skillDto);
        return ResponseEntity
                .created(new URI("/api/skills/" + result.getId()))
                .body(result);
    }
}
