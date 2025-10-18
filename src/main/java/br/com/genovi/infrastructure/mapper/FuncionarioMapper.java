package br.com.genovi.infrastructure.mapper;

import br.com.genovi.domain.models.Funcionario;
import br.com.genovi.dtos.funcionario.CreateFuncionarioDTO;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
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
}
