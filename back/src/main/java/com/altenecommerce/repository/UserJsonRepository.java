package com.altenecommerce.repository;

import com.altenecommerce.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserJsonRepository {
    private final File file = new File("src/main/resources/users.json");
    private final ObjectMapper mapper = new ObjectMapper();

    public List<User> findAll() {
        try {
            if (!file.exists()) return new ArrayList<>();
            return mapper.readValue(file, new TypeReference<List<User>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to read users.json", e);
        }
    }

    public Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public User save(User user) {
        List<User> users = findAll();
        long nextId = users.stream()
                .mapToLong(u -> u.getId() != null ? u.getId() : 0L)
                .max()
                .orElse(0L) + 1;
        user.setId(nextId);
        users.add(user);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write to users.json", e);
        }
        return user;
    }
}
