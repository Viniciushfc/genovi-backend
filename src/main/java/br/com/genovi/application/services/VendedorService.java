package br.com.genovi.application.services;

import br.com.genovi.domain.models.Vendedor;
import br.com.genovi.dtos.vendedor.CreateVendedorDTO;
import br.com.genovi.dtos.vendedor.VendedorDTO;
import br.com.genovi.infrastructure.mappers.VendedorMapper;
import br.com.genovi.infrastructure.repositories.VendedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorService {

    private final VendedorRepository vendedorRepository;
    private final VendedorMapper vendedorMapper;

    public VendedorService(VendedorRepository vendedorRepository, VendedorMapper vendedorMapper) {
        this.vendedorRepository = vendedorRepository;
        this.vendedorMapper = vendedorMapper;
    }

    private Vendedor findVendedorById(Long id) {
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));
    }

    public List<VendedorDTO> findAll() {
        return vendedorRepository.findAll().stream().map(vendedorMapper::toDTO).toList();
    }

    public VendedorDTO findById(Long id) {
        return vendedorMapper.toDTO(findVendedorById(id));
    }

    public VendedorDTO save(CreateVendedorDTO dto) {
        Vendedor vendedor = vendedorMapper.toEntity(dto);
        vendedor.setAtivo(true);

        vendedor = vendedorRepository.save(vendedor);

        return vendedorMapper.toDTO(vendedor);
    }

    public VendedorDTO update(Long id, CreateVendedorDTO dto) {
        Vendedor entity = findVendedorById(id);

        vendedorMapper.updateEntityFromDTO(dto, entity);

        return vendedorMapper.toDTO(vendedorRepository.save(entity));
    }

    public void disableById(Long id) {
        Vendedor vendedor = findVendedorById(id);

        vendedor.setAtivo(false);
        vendedorRepository.save(vendedor);
    }

    public void delete(Long id) {
        Vendedor vendedor = findVendedorById(id);
        vendedorRepository.deleteById(id);
    }
}
