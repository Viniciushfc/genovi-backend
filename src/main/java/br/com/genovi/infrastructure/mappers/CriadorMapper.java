package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Criador;
import br.com.genovi.dtos.criador.CriadorDTO;
import org.springframework.stereotype.Component;

@Component
public class CriadorMapper {

    public Criador toEntity(CriadorDTO dto) {
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
                entity.getNome(),
                entity.getCpfCnpj(),
                entity.getEndereco(),
                entity.getTelefone()
        );
    }

    public void updateEntityFromDTO(CriadorDTO dto, Criador entity) {
        entity.setNome(dto.nome());
        entity.setCpfCnpj(dto.cpfCnpj());
        entity.setEndereco(dto.endereco());
        entity.setTelefone(dto.telefone());
    }
}
