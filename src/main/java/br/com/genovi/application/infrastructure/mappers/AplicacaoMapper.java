package br.com.genovi.application.infrastructure.mappers;

import br.com.genovi.application.domain.models.Aplicacao;
import br.com.genovi.application.domain.models.Medicamento;
import br.com.genovi.application.domain.models.Ovino;
import br.com.genovi.application.domain.models.Usuario;
import br.com.genovi.application.dtos.AplicacaoDTO;
import br.com.genovi.application.dtos.CreateAplicacaoDTO;
import org.springframework.stereotype.Component;

@Component
public class AplicacaoMapper {

    private final OvinoMapper ovinoMapper;
    private final MedicamentoMapper medicamentoMapper;
    private final UsuarioMapper usuarioMapper;

    public AplicacaoMapper(OvinoMapper ovinoMapper, MedicamentoMapper medicamentoMapper, UsuarioMapper usuarioMapper) {
        this.ovinoMapper = ovinoMapper;
        this.medicamentoMapper = medicamentoMapper;
        this.usuarioMapper = usuarioMapper;
    }

    public Aplicacao toEntity(CreateAplicacaoDTO dto, Ovino ovino, Medicamento medicamento, Usuario usuario) {
        return new Aplicacao(
                null,
                dto.dataAplicacao(),
                ovino,
                medicamento,
                dto.temProximaDose(),
                dto.dataProximaDose(),
                usuario,
                dto.observacoes()
        );
    }

    public AplicacaoDTO toDTO(Aplicacao entity) {
        return new AplicacaoDTO(
                entity.getDataAplicacao(),
                ovinoMapper.toDTO(entity.getOvino()),
                medicamentoMapper.toDTO(entity.getMedicamento()),
                entity.isTemProximaDose(),
                entity.getDataProximaDose(),
                usuarioMapper.toDTO(entity.getResponsavel()),
                entity.getObservacoes()
        );
    }

    public void updateEntityFromDTO(CreateAplicacaoDTO dto, Aplicacao entity, Ovino ovino, Medicamento medicamento, Usuario usuario) {
        entity.setDataAplicacao(dto.dataAplicacao());
        entity.setOvino(ovino);
        entity.setMedicamento(medicamento);
        entity.setTemProximaDose(dto.temProximaDose());
        entity.setDataProximaDose(dto.dataProximaDose());
        entity.setResponsavel(usuario);
        entity.setObservacoes(dto.observacoes());
    }
}
