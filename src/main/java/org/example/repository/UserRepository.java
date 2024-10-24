package org.example.repository;

import org.example.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static final List<User> db = new ArrayList<>();
    private static int currentId = 2;

    static {
        db.add(User.builder().id(1).name("dqeq").surname("eqwtq").email("dsae@gmail.com").phoneNumber("+313123123").build());
        db.add(User.builder().id(2).name("gds").surname("vcxvcx").email("eqweq@yandex.ru").phoneNumber("+375213120").build());
    }

    public void save(User user) {
        currentId++;
        user.setId(currentId);
        db.add(user);
    }

    public Optional<User> findById(int id) {
        return db.stream().filter(user -> user.getId() == id).findFirst();
    }

    public List<User> findAll() {
        return new ArrayList<>(db);
    }
}
