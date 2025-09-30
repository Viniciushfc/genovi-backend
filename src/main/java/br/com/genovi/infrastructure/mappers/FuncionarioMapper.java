package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.dtos.funcionario.CreateFuncionarioDTO;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {

    public Funcionario toEntity(CreateFuncionarioDTO dto) {
        return new Funcionario(
                null,
                dto.nome(),
                dto.cpfCnpj(),
                dto.endereco(),
                dto.telefone(),
                dto.dataAdmissao(),
                dto.dataRecisao()
        );
    }

    public FuncionarioDTO toDTO(Funcionario entity) {
        if (entity == null) {
            return null;
        }

        return new FuncionarioDTO(
                entity.getId(),
                entity.getNome(),
                entity.getCpfCnpj(),
                entity.getEndereco(),
                entity.getTelefone(),
                entity.getDataAdmissao(),
                entity.getDataRecisao()
        );
    }

    public void updateEntityFromDTO(CreateFuncionarioDTO dto, Funcionario entity) {
        entity.setNome(dto.nome());
        entity.setCpfCnpj(dto.cpfCnpj());
        entity.setEndereco(dto.endereco());
        entity.setTelefone(dto.telefone());
        entity.setDataAdmissao(dto.dataAdmissao());
        entity.setDataRecisao(dto.dataRecisao());
    }
}
