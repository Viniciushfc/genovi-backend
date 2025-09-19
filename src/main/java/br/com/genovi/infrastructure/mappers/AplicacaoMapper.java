package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Aplicacao;
import br.com.genovi.domain.models.Medicamento;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.dtos.aplicacao.AplicacaoDTO;
import br.com.genovi.dtos.aplicacao.CreateAplicacaoDTO;
import org.springframework.stereotype.Component;

@Component
public class AplicacaoMapper {

    private final OvinoMapper ovinoMapper;
    private final MedicamentoMapper medicamentoMapper;

    public AplicacaoMapper(OvinoMapper ovinoMapper, MedicamentoMapper medicamentoMapper) {
        this.ovinoMapper = ovinoMapper;
        this.medicamentoMapper = medicamentoMapper;

    }

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
        return new AplicacaoDTO(
                entity.getDataAplicacao(),
                ovinoMapper.toDTO(entity.getOvino()),
                medicamentoMapper.toDTO(entity.getMedicamento()),
                entity.getDataProximaDose()

        );
    }

    public void updateEntityFromDTO(CreateAplicacaoDTO dto, Aplicacao entity, Ovino ovino, Medicamento medicamento) {
        entity.setDataAplicacao(dto.dataAplicacao());
        entity.setOvino(ovino);
        entity.setMedicamento(medicamento);
        entity.setDataProximaDose(dto.dataProximaDose());

    }
}
