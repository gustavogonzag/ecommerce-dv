package com.gustavo.ecommerce.controller;

import com.gustavo.ecommerce.dto.request.CategoriaRequestDTO;
import com.gustavo.ecommerce.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public ResponseEntity<List<CategoriaRequestDTO>> findAll(){
        return ResponseEntity.ok(service.listarCategorias());
    }

    @PostMapping
    public ResponseEntity<CategoriaRequestDTO> cadastrarCategoria(@Valid @RequestBody CategoriaRequestDTO dto){
        CategoriaRequestDTO res = service.criarCategoria(dto);
        if(res != null){
            return ResponseEntity.status(201).body(res);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaRequestDTO> atualizarCategoria(@PathVariable int id, @RequestBody CategoriaRequestDTO dto){
        dto.setId(id);
        CategoriaRequestDTO res = service.atualizarCategoria(dto);
        if(res != null) {
            return ResponseEntity.status(200).body(res);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCategoria(@PathVariable int id){
        service.apagarCategoria(id);
        return ResponseEntity.ok().body("Removed!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaRequestDTO> buscarCategoriaPorId(@PathVariable Integer id) {
        CategoriaRequestDTO categoria = service.buscarCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }

}
