package com.example.techinterview.repositories;

import com.example.techinterview.entities.Lokasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LokasiRepository extends JpaRepository<Lokasi, Long> {
}
