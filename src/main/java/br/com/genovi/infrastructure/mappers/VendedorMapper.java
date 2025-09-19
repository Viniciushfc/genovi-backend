package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Vendedor;
import br.com.genovi.dtos.vendedor.CreateVendedorDTO;
import br.com.genovi.dtos.vendedor.VendedorDTO;
import org.springframework.stereotype.Component;

@Component
public class VendedorMapper {

    public Vendedor toEntity(CreateVendedorDTO dto) {
        return new Vendedor(
                null,
                dto.ativo(),
                dto.nome(),
                dto.cpfCnpj(),
                dto.email(),
                dto.endereco(),
                dto.telefone()
        );
    }

    public VendedorDTO toDTO(Vendedor vendedor) {
        return new VendedorDTO(
                vendedor.getId(),
                vendedor.isAtivo(),
                vendedor.getNome(),
                vendedor.getCpfCnpj(),
                vendedor.getEmail(),
                vendedor.getEndereco(),
                vendedor.getTelefone()
        );
    }

    public void updateEntityFromDTO(CreateVendedorDTO dto, Vendedor entity) {
        entity.setAtivo(dto.ativo());
        entity.setNome(dto.nome());
        entity.setCpfCnpj(dto.cpfCnpj());
        entity.setEmail(dto.email());
        entity.setEndereco(dto.endereco());
        entity.setTelefone(dto.telefone());
    }
}
