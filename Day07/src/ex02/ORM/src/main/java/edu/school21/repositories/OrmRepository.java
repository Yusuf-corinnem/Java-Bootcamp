package edu.school21.repositories;

public interface OrmRepository {
    public void save(Object entity);

    public void update(Object entity);

    public <T> T findById(Long id, Class<T> aClass);

}
