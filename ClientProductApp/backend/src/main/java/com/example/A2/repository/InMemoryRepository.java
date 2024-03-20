//package com.example.A2.repository;
//
//import com.example.A2.domain.Student;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@org.springframework.stereotype.Repository
//public class InMemoryRepository implements IRepository{
//    private final Map<Integer,Student> repository;
//
//    public InMemoryRepository()
//    {
//        repository = new HashMap<>();
//        repository.put(1, new Student(1,"sa","sadas","dss",25.4,946));
//    }
//
//    @Override
//    public Optional<Student> get(Integer id)
//    {
//        return Optional.ofNullable(repository.get(id));
//    }
//
//    @Override
//    public void add(Student newStudent)
//    {
//        repository.put(newStudent.getId(), newStudent);
//    }
//
//    @Override
//    public void delete(Integer id)
//    {
//        repository.remove(id);
//    }
//
//    @Override
//    public void update(Student newStudent) {
//        repository.replace(newStudent.getId(), newStudent);
//    }
//
//    @Override
//    public Map<Integer,Student> getRepository() {return repository;}
//
//    @Override
//    public boolean findByID(Integer id) {
//        if (repository.containsKey(id)) return true;
//        else return false;
//    }
//}
