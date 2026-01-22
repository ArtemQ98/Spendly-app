package ru.artteam.spendly_app.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.artteam.spendly_app.domain.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"transactions"})
    List<UserEntity> findAll();
}
