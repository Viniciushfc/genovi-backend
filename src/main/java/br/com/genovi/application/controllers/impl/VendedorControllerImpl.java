package br.com.genovi.application.controllers.impl;

import br.com.genovi.application.controllers.VendedorController;
import br.com.genovi.dtos.vendedor.CreateVendedorDTO;
import br.com.genovi.dtos.vendedor.VendedorDTO;
import br.com.genovi.application.services.VendedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VendedorControllerImpl implements VendedorController {

    private final VendedorService vendedorService;

    public VendedorControllerImpl(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    @Override
    public ResponseEntity<List<VendedorDTO>> findAll() {
        return ResponseEntity.ok(vendedorService.findAll());
    }

    @Override
    public ResponseEntity<VendedorDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vendedorService.findById(id));
    }

    @Override
    public ResponseEntity<VendedorDTO> save(@RequestBody CreateVendedorDTO dto) {
        VendedorDTO saved = vendedorService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Override
    public ResponseEntity<VendedorDTO> update(@PathVariable Long id, @RequestBody CreateVendedorDTO dto) {
        return ResponseEntity.ok(vendedorService.update(id, dto));
    }

    @Override
    public ResponseEntity<VendedorDTO> disable(@PathVariable Long id) {
        vendedorService.disableById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vendedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}