package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmValidationException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final UserStorage userStorage;
    private Integer filmId = 0;
    private final Map<Integer, Film> films = new HashMap<>();

    public Collection<Film> getAll() {
        return films.values();
    }

    public Film create(Film film) throws ValidationException {
        FilmValidationException.validationException(film);
        for (Film f : films.values()) {
            if (f.equals(film)) {
                log.debug("ValidationException");
                throw new ValidationException("Фильм уже есть в базе.");
            }
        }
        film.setId(++filmId);
        films.put(filmId, film);
        log.info("Получен запрос POST /films");
        return film;
    }

    public Film put(Film film) throws ValidationException {
        FilmValidationException.validationException(film);
        if (!films.containsKey(film.getId())) {
            log.debug("FilmNotFoundException");
            throw new FilmNotFoundException("Фильма с таким id нет в базе.");
        }
        films.put(film.getId(), film);
        log.info("Получен запрос PUT /films");
        return film;
    }

    public Film findFilmById(Integer filmId) {
        if (filmId == null || filmId <= 0) {
            throw new FilmNotFoundException(String.format("Передан null или отрицательный id фильма"));
        }
        Film film = films.get(filmId);
        if (film == null) {
            throw new FilmNotFoundException(String.format("Фильм с id %d не найден", filmId));
        }
        return film;
    }

    public User findUserById(Integer userId) {
        return userStorage.findUserById(userId);
    }

}
