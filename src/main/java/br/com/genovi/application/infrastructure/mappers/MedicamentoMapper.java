package br.com.genovi.application.infrastructure.mappers;

import br.com.genovi.application.domain.models.Doenca;
import br.com.genovi.application.domain.models.Medicamento;
import br.com.genovi.application.dtos.CreateMedicamentoDTO;
import br.com.genovi.application.dtos.MedicamentoDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicamentoMapper {

    private final DoencaMapper doencaMapper;

    public MedicamentoMapper(DoencaMapper doencaMapper) {
        this.doencaMapper = doencaMapper;
    }

    public Medicamento toEntity(CreateMedicamentoDTO dto, List<Doenca> doencas) {
        return new Medicamento(null,
                dto.nome(),
                dto.fabricante(),
                doencas,
                dto.doseUnica(),
                dto.descricao()
        );
    }

    public MedicamentoDTO toDTO(Medicamento entity) {
        return new MedicamentoDTO(
                entity.getNome(),
                entity.getFabricante(),
                entity.getDoencas().stream()
                        .map(doencaMapper::toDTO)
                        .toList(),
                entity.isDoceUnica(),
                entity.getDescricao()
        );
    }

    public void updateEntityFromDTO(CreateMedicamentoDTO dto, Medicamento entity, List<Doenca> doencas) {
        entity.setNome(dto.nome());
        entity.setFabricante(dto.fabricante());
        entity.setDoencas(doencas);
        entity.setDoceUnica(dto.doseUnica());
        entity.setDescricao(dto.descricao());
    }
}

