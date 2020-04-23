package service.persistence;

import service.persistence.exception.DataErrorException;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> getById(Long id);

    List<T> getAll();

    void save(T t) throws DataErrorException;

    void update(T t, String[] params) throws DataErrorException;

    void delete(T t) throws DataErrorException;
}
