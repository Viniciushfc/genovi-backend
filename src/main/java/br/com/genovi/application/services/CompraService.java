package br.com.genovi.application.services;

import br.com.genovi.domain.models.Compra;
import br.com.genovi.domain.models.Vendedor;
import br.com.genovi.dtos.compra.CompraDTO;
import br.com.genovi.dtos.compra.CreateCompraDTO;
import br.com.genovi.infrastructure.mappers.CompraMapper;
import br.com.genovi.infrastructure.repositories.CompraRepository;
import br.com.genovi.infrastructure.repositories.VendedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final VendedorRepository vendedorRepository;
    private final CompraMapper compraMapper;

    public CompraService(CompraRepository compraRepository, VendedorRepository vendedorRepository, CompraMapper compraMapper) {
        this.compraRepository = compraRepository;
        this.vendedorRepository = vendedorRepository;
        this.compraMapper = compraMapper;
    }

    private Compra findCompraById(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra não encontrada"));
    }

    private Vendedor findVendedorById(Long id) {
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrada"));
    }

    public List<CompraDTO> findAll() {
        return compraRepository.findAll().stream().map(compraMapper::toDTO).toList();
    }

    public CompraDTO findById(Long id) {
        return compraMapper.toDTO(findCompraById(id));
    }

    public CompraDTO save(CreateCompraDTO dto) {
        Vendedor vendedor = findVendedorById(dto.vendedorId());

        Compra compra = compraMapper.toEntity(dto, vendedor);

        return compraMapper.toDTO(compraRepository.save(compra));
    }

    public CompraDTO update(Long id, CreateCompraDTO dto) {
        Compra entity = findCompraById(id);
        Vendedor vendedor = findVendedorById(dto.vendedorId());

        compraMapper.updateEntityFromDTO(dto, entity, vendedor);

        return compraMapper.toDTO(compraRepository.save(entity));
    }

    public void delete(Long id) {
        findCompraById(id);
        compraRepository.deleteById(id);
    }
}
