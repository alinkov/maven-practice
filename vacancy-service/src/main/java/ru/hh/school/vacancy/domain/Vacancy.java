package ru.hh.school.vacancy.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {
    @Id
    @GeneratedValue
    private Long id;
    private String position;
    @ManyToOne
    private Company company;
    private Long salaryFrom;
    private Long salaryTo;
    private String description;
    @OneToMany
    private List<Skill> skills;
}
