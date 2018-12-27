package ru.hh.school.vacancy.service.mapper;

public interface Mapper<E, D> {
    /**
     * Преобразование DTO в Entity-объект.
     * @param dto DTO
     * @return Entity
     */
    E toEntity(D dto);

    /**
     * Преобразование Entity-объекта в DTO.
     * @param entity Entity
     * @return Dto
     */
    D toDto(E entity);
}
