package br.com.genovi.application.mapper;

import br.com.genovi.domain.models.Aplicacao;
import br.com.genovi.domain.models.Medicamento;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.aplicacao.AplicacaoDTO;
import br.com.genovi.dtos.aplicacao.CreateAplicacaoDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AplicacaoMapper {

    private final OvinoMapper ovinoMapper;
    private final MedicamentoMapper medicamentoMapper;

    public Aplicacao toEntity(CreateAplicacaoDTO dto, Ovino ovino, Medicamento medicamento) {
        return new Aplicacao(
                null,
                ovino,
                dto.dataAplicacao(),
                medicamento,
                dto.dataProximaDose()
        );
    }

    public AplicacaoDTO toDTO(Aplicacao entity) {
        if (entity == null) {
            return null;
        }

        return new AplicacaoDTO(
                entity.getId(),
                entity.getDataAplicacao(),
                ovinoMapper.toDTO(entity.getOvino()),
                medicamentoMapper.toDTO(entity.getMedicamento()),
                entity.getDataProximaDose()
        );
    }
}
