package com.example.techinterview.controllers;

import com.example.techinterview.entities.Lokasi;
import com.example.techinterview.entities.Proyek;
import com.example.techinterview.repositories.LokasiRepository;
import com.example.techinterview.repositories.ProyekRepository;
import com.example.techinterview.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lokasi")
public class LokasiController {

    @Autowired
    private LokasiRepository lokasiRepository;

    @Autowired
    private ProyekRepository proyekRepository;

    @PostMapping
    @Transactional
    public Lokasi createLokasi(@RequestBody Lokasi lokasi) {
        List<Proyek> proyekList = lokasi.getProyek();
        if (proyekList != null) {
            for (Proyek proyek : proyekList) {
                Optional<Proyek> existingProyek = proyekRepository.findById(proyek.getId());
                existingProyek.ifPresent(value -> proyekRepository.save(value));
            }
        }
        return lokasiRepository.save(lokasi);
    }

    @GetMapping
    public List<Lokasi> getAllLokasi() {
        return lokasiRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lokasi> getLokasiById(@PathVariable Long id) {
        Lokasi lokasi = lokasiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lokasi not found for this id :: " + id));
        return ResponseEntity.ok(lokasi);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Lokasi> updateLokasi(@PathVariable Long id, @RequestBody Lokasi lokasiDetails) {
        Lokasi lokasi = lokasiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lokasi not found for this id :: " + id));

        lokasi.setNamaLokasi(lokasiDetails.getNamaLokasi());
        lokasi.setNegara(lokasiDetails.getNegara());
        lokasi.setProvinsi(lokasiDetails.getProvinsi());
        lokasi.setKota(lokasiDetails.getKota());

        List<Proyek> proyekList = lokasiDetails.getProyek();
        if (proyekList != null) {
            lokasi.setProyek(proyekList);
            for (Proyek proyek : proyekList) {
                Optional<Proyek> existingProyek = proyekRepository.findById(proyek.getId());
                existingProyek.ifPresent(value -> proyekRepository.save(value));
            }
        }

        final Lokasi updatedLokasi = lokasiRepository.save(lokasi);
        return ResponseEntity.ok(updatedLokasi);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteLokasi(@PathVariable Long id) {
        Lokasi lokasi = lokasiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lokasi not found for this id :: " + id));

        lokasiRepository.delete(lokasi);
        return ResponseEntity.noContent().build();
    }
}
