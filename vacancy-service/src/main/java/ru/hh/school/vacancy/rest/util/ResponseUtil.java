package ru.hh.school.vacancy.rest.util;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseUtil {
    private ResponseUtil() {
    }

    /**
     * Возвращает ResponseEntity для REST-endpoints.
     * @param maybeResponse Optional с сущностью
     * @param <X> тип сущности
     * @return ResponseEntity с сущностью в теле либо пустое тело со статусом 404 (Not Found)
     */
    @SuppressWarnings("unchecked")
    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return maybeResponse
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}
