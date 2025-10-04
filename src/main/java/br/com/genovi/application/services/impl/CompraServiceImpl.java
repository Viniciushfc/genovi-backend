package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.CompraService;
import br.com.genovi.domain.models.Compra;
import br.com.genovi.domain.models.Vendedor;
import br.com.genovi.dtos.compra.CompraDTO;
import br.com.genovi.dtos.compra.CreateCompraDTO;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.infrastructure.mappers.CompraMapper;
import br.com.genovi.infrastructure.repositories.CompraRepository;
import br.com.genovi.infrastructure.repositories.VendedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;
    private final VendedorRepository vendedorRepository;
    private final CompraMapper compraMapper;

    public CompraServiceImpl(CompraRepository compraRepository, VendedorRepository vendedorRepository, CompraMapper compraMapper) {
        this.compraRepository = compraRepository;
        this.vendedorRepository = vendedorRepository;
        this.compraMapper = compraMapper;
    }

    private Compra findCompraById(Long id) {
        if (id == null) return null;
        return compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra não encontrada"));
    }

    private Vendedor findVendedorById(Long id) {
        if (id == null) return null;
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrada"));
    }

    @Override
    public List<CompraDTO> findAll() {
        return compraRepository.findAll().stream().map(compraMapper::toDTO).toList();
    }

    @Override
    public CompraDTO findById(Long id) {
        return compraMapper.toDTO(findCompraById(id));
    }

    @Override
    public CompraDTO save(CreateCompraDTO dto) {
        Vendedor vendedor = findVendedorById(dto.vendedorId());

        Compra compra = compraMapper.toEntity(dto, vendedor);

        return compraMapper.toDTO(compraRepository.save(compra));
    }

    @Override
    public CompraDTO update(Long id, CreateCompraDTO dto) {
        Compra entity = findCompraById(id);
        Vendedor vendedor = findVendedorById(dto.vendedorId());

        Long existingId = entity.getId();
        Compra updatedCompra = compraMapper.toEntity(dto, vendedor);
        updatedCompra.setId(existingId);
        compraRepository.save(updatedCompra);

        return compraMapper.toDTO(updatedCompra);
    }

    @Override
    public void delete(Long id) {
        findCompraById(id);
        compraRepository.deleteById(id);
    }
}
