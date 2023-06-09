package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
public class FilmValidationException {
    public static void validationException(@RequestBody Film film) throws ValidationException {
        if (film == null) {
            log.debug("ValidationException");
            throw new ValidationException("Запрос не может быть пустым.");
        }
        if (film.getName() == null || film.getName().isBlank()) {
            log.debug("ValidationException");
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        if (film.getDescription() != null) {
            if (film.getDescription().length() > 200) {
                log.debug("ValidationException");
                throw new ValidationException("Максимальная длина описания фильма — 200 символов.");
            }
        }
        if (film.getReleaseDate() != null) {
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                log.debug("ValidationException");
                throw new ValidationException("Дата релиза фильма не может быть раньше 28.12.1895 года.");
            }
        }
        if (film.getDuration() != null) {
            if (film.getDuration() <= 0) {
                log.debug("ValidationException");
                throw new ValidationException("Продолжительность фильма должна быть положительной.");
            }
        }
    }
}
