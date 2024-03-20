package com.example.A2.repository;

import java.util.Map;
import java.util.Optional;

public interface IRepository<T> {
    Optional<T> get(Integer id);
    void add(T obj);

    void delete(Integer id);
    void update(T newEntity);
    Map<Integer,T> getRepository();

    boolean findByID(Integer id);
}
