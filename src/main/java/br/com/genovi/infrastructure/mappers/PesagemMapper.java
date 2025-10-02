package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Pesagem;
import br.com.genovi.dtos.ovino.OvinoResumoDTO;
import br.com.genovi.dtos.pesagem.CreatePesagemDTO;
import br.com.genovi.dtos.pesagem.PesagemDTO;
import org.springframework.stereotype.Component;

@Component
public class PesagemMapper {

    private final OvinoMapper ovinoMapper;

    public PesagemMapper(OvinoMapper ovinoMapper) {
        this.ovinoMapper = ovinoMapper;
    }

    public Pesagem toEntity(CreatePesagemDTO dto, Ovino ovino) {
        return new Pesagem(
                null,
                dto.dataPesagem(),
                ovino);
    }

    public PesagemDTO toDTO(Pesagem entity) {
        if (entity == null) {
            return null;
        }
        Ovino ovino = entity.getOvino();
        OvinoResumoDTO ovinoResumo = ovino != null
                ? new OvinoResumoDTO(ovino.getId(), ovino.getRfid(), ovino.getNome(), ovino.getFbb())
                : null;
        return new PesagemDTO(
                entity.getId(),
                entity.getDataPesagem(),
                ovinoResumo
        );
    }
}
