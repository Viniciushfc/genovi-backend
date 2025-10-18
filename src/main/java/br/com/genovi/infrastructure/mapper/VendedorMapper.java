package br.com.genovi.infrastructure.mapper;

import br.com.genovi.domain.models.Vendedor;
import br.com.genovi.dtos.vendedor.CreateVendedorDTO;
import br.com.genovi.dtos.vendedor.VendedorDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
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
}
