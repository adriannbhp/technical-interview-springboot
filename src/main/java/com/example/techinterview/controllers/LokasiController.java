package com.example.techinterview.controllers;

import com.example.techinterview.entities.Lokasi;
import com.example.techinterview.repositories.LokasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.techinterview.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lokasi")
public class LokasiController {

    @Autowired
    private LokasiRepository lokasiRepository;

    @PostMapping
    public Lokasi createLokasi(@RequestBody Lokasi lokasi) {
        return lokasiRepository.save(lokasi);
    }

    @GetMapping
    public List<Lokasi> getAllLokasi() {
        return lokasiRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lokasi> updateLokasi(@PathVariable Long id, @RequestBody Lokasi lokasiDetails) {
        Lokasi lokasi = lokasiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lokasi not found for this id :: " + id));

        lokasi.setNamaLokasi(lokasiDetails.getNamaLokasi());
        lokasi.setNegara(lokasiDetails.getNegara());
        lokasi.setProvinsi(lokasiDetails.getProvinsi());
        lokasi.setKota(lokasiDetails.getKota());

        final Lokasi updatedLokasi = lokasiRepository.save(lokasi);
        return ResponseEntity.ok(updatedLokasi);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLokasi(@PathVariable Long id) {
        Lokasi lokasi = lokasiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lokasi not found for this id :: " + id));

        lokasiRepository.delete(lokasi);
        return ResponseEntity.noContent().build();
    }
}
