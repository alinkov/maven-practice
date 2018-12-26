package ru.hh.school.vacancy.rest;

import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.school.vacancy.VacancyServiceApplication;
import ru.hh.school.vacancy.domain.Company;
import ru.hh.school.vacancy.domain.Skill;
import ru.hh.school.vacancy.domain.Vacancy;
import ru.hh.school.vacancy.repository.VacancyRepository;
import ru.hh.school.vacancy.service.VacancyService;
import ru.hh.school.vacancy.service.dto.VacancyDTO;
import ru.hh.school.vacancy.service.mapper.VacancyMapper;
import ru.hh.school.vacancy.testutils.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VacancyServiceApplication.class)
public class VacancyResourceIntTest {
    private static final String DEFAULT_POSITION = "Position";
    private static final String DEFAULT_DESCRIPTION = "Description";
    private static final Long DEFAULT_SALARY_FROM = 1000L;
    private static final Long DEFAULT_SALARY_TO = 2000L;

    @Autowired
    private VacancyMapper vacancyMapper;
    @Autowired
    private VacancyService vacancyService;
    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private EntityManager em;

    private MockMvc vacancyMockMvc;

    private Vacancy vacancy;

    public static Vacancy createEntity(EntityManager em) {
        Skill skill = SkillResourceIntTest.createEntity(em);
        em.persist(skill);
        em.flush();

        Company company = CompanyResourceIntTest.createEntity(em);
        em.persist(company);
        em.flush();

        List<Skill> skills = Collections.singletonList(skill);
        return Vacancy.builder()
                .position(DEFAULT_POSITION)
                .description(DEFAULT_DESCRIPTION)
                .salaryFrom(DEFAULT_SALARY_FROM)
                .salaryTo(DEFAULT_SALARY_TO)
                .company(company)
                .skills(skills)
                .build();
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VacancyResource vacancyResource = new VacancyResource(vacancyService);
        this.vacancyMockMvc = MockMvcBuilders.standaloneSetup(vacancyResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        vacancy = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppeal() throws Exception {
        int databaseSizeBeforeCreate = vacancyRepository.findAll().size();

        VacancyDTO vacancyDTO = vacancyMapper.toDto(vacancy);
        vacancyMockMvc.perform(post("/api/vacancy")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtil.objectToBytes(vacancyDTO)))
                .andExpect(status().isCreated());

        List<Vacancy> vacancyList = vacancyRepository.findAll();
        assertThat(vacancyList).hasSize(databaseSizeBeforeCreate + 1);

        Vacancy testVacancy = vacancyList.get(vacancyList.size() - 1);
        assertThat(testVacancy.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testVacancy.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVacancy.getSalaryFrom()).isEqualTo(DEFAULT_SALARY_FROM);
        assertThat(testVacancy.getSalaryTo()).isEqualTo(DEFAULT_SALARY_TO);

        assertThat(testVacancy.getCompany()).isEqualTo(vacancy.getCompany());
        assertThat(testVacancy.getSkills().size()).isEqualTo(vacancy.getSkills().size());
        assertThat(testVacancy.getSkills().get(0)).isEqualTo(vacancy.getSkills().get(0));
    }

    @Test
    @Transactional
    public void getAllAnswers() throws Exception {
        vacancyRepository.saveAndFlush(vacancy);

        vacancyMockMvc.perform(get("/api/vacancy"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(vacancy.getId().intValue())))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
                .andExpect(jsonPath("$.[*].salaryFrom").value(hasItem(DEFAULT_SALARY_FROM.intValue())))
                .andExpect(jsonPath("$.[*].salaryTo").value(hasItem(DEFAULT_SALARY_TO.intValue())));
    }

    @Test
    @Transactional
    public void getAnswer() throws Exception {
        vacancyRepository.saveAndFlush(vacancy);

        vacancyMockMvc.perform(get("/api/vacancy/{id}", vacancy.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(vacancy.getId().intValue()))
                .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
                .andExpect(jsonPath("$.salaryFrom").value(DEFAULT_SALARY_FROM.intValue()))
                .andExpect(jsonPath("$.salaryTo").value(DEFAULT_SALARY_TO.intValue()));
    }


}
