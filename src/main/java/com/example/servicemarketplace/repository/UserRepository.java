package com.example.servicemarketplace.repository;

import com.example.servicemarketplace.model.Userentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Userentity, Long> {
    Optional<Userentity> findByUsername(String username);
}
