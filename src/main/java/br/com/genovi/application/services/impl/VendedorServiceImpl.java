package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.VendedorService;
import br.com.genovi.domain.models.Vendedor;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.dtos.vendedor.CreateVendedorDTO;
import br.com.genovi.dtos.vendedor.VendedorDTO;
import br.com.genovi.infrastructure.mappers.VendedorMapper;
import br.com.genovi.infrastructure.repositories.VendedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorServiceImpl implements VendedorService {

    private final VendedorRepository vendedorRepository;
    private final VendedorMapper vendedorMapper;

    public VendedorServiceImpl(VendedorRepository vendedorRepository, VendedorMapper vendedorMapper) {
        this.vendedorRepository = vendedorRepository;
        this.vendedorMapper = vendedorMapper;
    }

    private Vendedor findVendedorById(Long id) {
        if (id == null) return null;
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado"));
    }

    @Override
    public List<VendedorDTO> findAll() {
        return vendedorRepository.findAll().stream().map(vendedorMapper::toDTO).toList();
    }

    @Override
    public VendedorDTO findById(Long id) {
        return vendedorMapper.toDTO(findVendedorById(id));
    }

    @Override
    public VendedorDTO save(CreateVendedorDTO dto) {
        Vendedor vendedor = vendedorMapper.toEntity(dto);
        vendedor.setAtivo(true);

        vendedor = vendedorRepository.save(vendedor);

        return vendedorMapper.toDTO(vendedor);
    }

    @Override
    public VendedorDTO update(Long id, CreateVendedorDTO dto) {
        Vendedor entity = findVendedorById(id);

        Long existingId = entity.getId();
        Vendedor updatedVendedor = vendedorMapper.toEntity(dto);
        updatedVendedor.setId(existingId);
        vendedorRepository.save(updatedVendedor);

        return vendedorMapper.toDTO(updatedVendedor);
    }

    @Override
    public void disableById(Long id) {
        Vendedor vendedor = findVendedorById(id);

        vendedor.setAtivo(false);
        vendedorRepository.save(vendedor);
    }

    @Override
    public void delete(Long id) {
        Vendedor vendedor = findVendedorById(id);
        vendedorRepository.deleteById(id);
    }
}
