package com.gustavo.ecommerce.controller;

import com.gustavo.ecommerce.dto.request.BairroRequestDTO;
import com.gustavo.ecommerce.service.BairroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bairros")
public class BairroController {

    @Autowired
    private BairroService bairroService;

    @GetMapping
    public ResponseEntity<List<BairroRequestDTO>> findAll() {
        return ResponseEntity.ok(bairroService.listarBairros());
    }

    @PostMapping
    public ResponseEntity<BairroRequestDTO> save(@Valid @RequestBody BairroRequestDTO dto) {

        BairroRequestDTO res = bairroService.cadastroBairro(dto);
        if (res != null) {
            return ResponseEntity.status(201).body(res);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BairroRequestDTO> update(@PathVariable Integer id, @RequestBody BairroRequestDTO dto) {
        dto.setId(id);
        BairroRequestDTO res = bairroService.atualizarBairro(dto);
        if (res != null) {
            return ResponseEntity.status(200).body(res);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BairroRequestDTO> delete(@PathVariable Integer id) {
        bairroService.apagarBairro(id);
        return ResponseEntity.noContent().build();
    }

}
