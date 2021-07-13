package repository;

import java.util.List;

public interface IRepository<ID, T> {
    void add(T entity);
    void delete(ID id);
    void deleteAll();
    void update(ID id,T entity);
    T findEntity(ID id);
    List<T> getAll();
}
