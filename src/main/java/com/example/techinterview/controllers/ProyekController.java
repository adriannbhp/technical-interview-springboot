package com.example.techinterview.controllers;

import com.example.techinterview.entities.Proyek;
import com.example.techinterview.repositories.ProyekRepository;
import com.example.techinterview.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proyek")
public class ProyekController {

    @Autowired
    private ProyekRepository proyekRepository;

    @PostMapping
    public Proyek createProyek(@RequestBody Proyek proyek) {
        return proyekRepository.save(proyek);
    }

    @GetMapping
    public List<Proyek> getAllProyek() {
        return proyekRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyek> updateProyek(@PathVariable Long id, @RequestBody Proyek proyekDetails) {
        Proyek proyek = proyekRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyek not found for this id :: " + id));

        proyek.setNamaProyek(proyekDetails.getNamaProyek());
        proyek.setClient(proyekDetails.getClient());
        proyek.setTglMulai(proyekDetails.getTglMulai());
        proyek.setTglSelesai(proyekDetails.getTglSelesai());
        proyek.setPimpinanProyek(proyekDetails.getPimpinanProyek());
        proyek.setKeterangan(proyekDetails.getKeterangan());

        final Proyek updatedProyek = proyekRepository.save(proyek);
        return ResponseEntity.ok(updatedProyek);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProyek(@PathVariable Long id) {
        Proyek proyek = proyekRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyek not found for this id :: " + id));

        proyekRepository.delete(proyek);
        return ResponseEntity.noContent().build();
    }
}
