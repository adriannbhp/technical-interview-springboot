package com.example.techinterview.repositories;

import com.example.techinterview.entities.Proyek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyekRepository extends JpaRepository<Proyek, Long> {
}
