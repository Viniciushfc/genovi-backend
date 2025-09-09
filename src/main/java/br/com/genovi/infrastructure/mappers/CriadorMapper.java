package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Criador;
import br.com.genovi.dtos.criador.CreateCriadorDTO;
import br.com.genovi.dtos.criador.CriadorDTO;
import org.springframework.stereotype.Component;

@Component
public class CriadorMapper {

    public Criador toEntity(CreateCriadorDTO dto) {
        return new Criador(
                null,
                dto.nome(),
                dto.cpfCnpj(),
                dto.endereco(),
                dto.telefone()
        );
    }

    public CriadorDTO toDTO(Criador entity) {
        return new CriadorDTO(
                entity.getId(),
                entity.getNome(),
                entity.getCpfCnpj(),
                entity.getEndereco(),
                entity.getTelefone()
        );
    }

    public void updateEntityFromDTO(CreateCriadorDTO dto, Criador entity) {
        entity.setNome(dto.nome());
        entity.setCpfCnpj(dto.cpfCnpj());
        entity.setEndereco(dto.endereco());
        entity.setTelefone(dto.telefone());
    }
}
