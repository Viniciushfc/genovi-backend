package br.com.genovi.application.infrastructure.mappers;

import br.com.genovi.application.domain.models.Ascendencia;
import br.com.genovi.application.domain.models.Criador;
import br.com.genovi.application.dtos.CreateOvinoDTO;
import br.com.genovi.application.dtos.OvinoDTO;
import br.com.genovi.application.domain.models.Ovino;
import org.springframework.stereotype.Component;

@Component
public class OvinoMapper {

    //Converter DTO para Entidade (para criação)
    public Ovino toEntity(CreateOvinoDTO dto,boolean ativo ,Criador criador, Ascendencia ascendencia) {
        return new Ovino(
                null,
                dto.rfid(),
                ativo,
                dto.nome(),
                dto.raca(),
                dto.fbb(),
                dto.dataNascimento(),
                criador,
                dto.tempoFazendo(),
                dto.typeGrauPureza(),
                dto.sexo(),
                dto.peso(),
                dto.comportamento(),
                ascendencia,
                dto.status()
        );
    }

    //Converter Entidade para DTO (para retorno)
    public OvinoDTO toDTO(Ovino ovino) {
        return new OvinoDTO(
                ovino.getRfid(),
                ovino.isAtivo(),
                ovino.getNome(),
                ovino.getRaca(),
                ovino.getFbb(),
                ovino.getDataNascimento(),
                ovino.getCriador(),
                ovino.getTempoFazendo(),
                ovino.getTypeGrauPureza(),
                ovino.getSexo(),
                ovino.getPeso(),
                ovino.getComportamento(),
                ovino.getAscendencia(),
                ovino.getStatus()
        );
    }

    //Atualizar uma entidade existente a partir do DTO
    public void updateEntityFromDTO(CreateOvinoDTO dto,boolean ativo ,Ovino entity, Criador criador, Ascendencia ascendencia) {
        entity.setRfid(dto.rfid());
        entity.setAtivo(ativo);
        entity.setNome(dto.nome());
        entity.setRaca(dto.raca());
        entity.setFbb(dto.fbb());
        entity.setDataNascimento(dto.dataNascimento());
        entity.setCriador(criador);
        entity.setTempoFazendo(dto.tempoFazendo());
        entity.setTypeGrauPureza(dto.typeGrauPureza());
        entity.setSexo(dto.sexo());
        entity.setPeso(dto.peso());
        entity.setComportamento(dto.comportamento());
        entity.setAscendencia(ascendencia);
        entity.setStatus(dto.status());
    }
}

