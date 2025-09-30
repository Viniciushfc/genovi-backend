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

    public VendedorDTO toDTO(Vendedor entity) {
        if (entity == null) {
            return null;
        }

        return new VendedorDTO(
                entity.getId(),
                entity.isAtivo(),
                entity.getNome(),
                entity.getCpfCnpj(),
                entity.getEmail(),
                entity.getEndereco(),
                entity.getTelefone()
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
