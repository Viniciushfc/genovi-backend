package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.VendedorService;
import br.com.genovi.domain.models.Vendedor;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.dtos.vendedor.CreateVendedorDTO;
import br.com.genovi.dtos.vendedor.VendedorDTO;
import br.com.genovi.application.mapper.VendedorMapper;
import br.com.genovi.infrastructure.repository.VendedorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class VendedorServiceImpl implements VendedorService {

    private final VendedorRepository vendedorRepository;
    private final VendedorMapper vendedorMapper;

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

        entity.setNome(dto.nome());
        entity.setAtivo(dto.ativo());
        entity.setCpfCnpj(dto.cpfCnpj());
        entity.setEmail(dto.email());
        entity.setEndereco(dto.endereco());
        entity.setTelefone(dto.telefone());

        return vendedorMapper.toDTO(vendedorRepository.save(entity));
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
