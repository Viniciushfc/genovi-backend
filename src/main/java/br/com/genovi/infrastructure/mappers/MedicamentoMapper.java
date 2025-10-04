package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Doenca;
import br.com.genovi.domain.models.Medicamento;
import br.com.genovi.dtos.medicamento.CreateMedicamentoDTO;
import br.com.genovi.dtos.medicamento.MedicamentoDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicamentoMapper {

    private final DoencaMapper doencaMapper;

    public MedicamentoMapper(DoencaMapper doencaMapper) {
        this.doencaMapper = doencaMapper;
    }

    public Medicamento toEntity(CreateMedicamentoDTO dto, List<Doenca> doencas) {
        return new Medicamento(
                null,
                dto.nome(),
                dto.fabricante(),
                doencas,
                dto.intervaloDoses(),
                dto.quantidadeDoses(),
                dto.isVacina()
        );
    }

    public MedicamentoDTO toDTO(Medicamento entity) {
        if (entity == null) {
            return null;
        }

        return new MedicamentoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getFabricante(),
                entity.getIntervaloDoses(),
                entity.getDoencas().stream()
                        .map(doencaMapper::toDTO)
                        .toList(),
                entity.getQuantidadeDoses(),
                entity.isVacina()
        );
    }
}

