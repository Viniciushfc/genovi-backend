package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.*;
import br.com.genovi.dtos.ovino.CreateOvinoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import org.springframework.context.annotation.Lazy; // IMPORT CORRETO
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OvinoMapper {

    private final PartoMapper partoMapper;
    private final PesagemMapper pesagemMapper;
    private final CompraMapper compraMapper;

    public OvinoMapper(@Lazy PartoMapper partoMapper, @Lazy PesagemMapper pesagemMapper, CompraMapper compraMapper) {
        this.partoMapper = partoMapper;
        this.pesagemMapper = pesagemMapper;
        this.compraMapper = compraMapper;
    }

    public Ovino toEntity(CreateOvinoDTO dto, Ovino ovinoMae, Ovino ovinoPai, Compra compra, Parto parto, List<Pesagem> pesagens) {
        return new Ovino(
                null,
                dto.rfid(),
                dto.nome(),
                dto.raca(),
                dto.fbb(),
                dto.dataNascimento(),
                dto.dataCadastro(),
                dto.typeGrauPureza(),
                dto.sexo(),
                ovinoMae,
                ovinoPai,
                dto.status(),
                dto.fotoOvino(),
                compra,
                parto,
                pesagens);
    }

    public OvinoDTO toDTO(Ovino entity) {
        if (entity == null) {
            return null;
        }
        return new OvinoDTO(
                entity.getId(),
                entity.getRfid(),
                entity.getNome(),
                entity.getRaca(),
                entity.getFbb(),
                entity.getDataNascimento(),
                entity.getDataCadastro(),
                entity.getTypeGrauPureza(),
                entity.getOvinoMae(),
                entity.getOvinoPai(),
                entity.getStatus(),
                entity.getFotoOvino(),
                compraMapper.toDTO(entity.getCompra()),
                partoMapper.toDTO(entity.getParto()),
                entity.getPesagens() == null
                        ? Collections.emptyList()
                        : entity.getPesagens().stream().map(pesagemMapper::toDTO).toList()
        );
    }


    public void updateEntityFromDTO(CreateOvinoDTO dto, Ovino entity, Ovino ovinoMae, Ovino ovinoPai, Compra compra, Parto parto, List<Pesagem> pesagens) {
        entity.setRfid(dto.rfid());
        entity.setNome(dto.nome());
        entity.setRaca(dto.raca());
        entity.setFbb(dto.fbb());
        entity.setDataNascimento(dto.dataNascimento());
        entity.setDataCadastro(dto.dataCadastro());
        entity.setTypeGrauPureza(dto.typeGrauPureza());
        entity.setSexo(dto.sexo());
        entity.setOvinoMae(ovinoMae);
        entity.setOvinoPai(ovinoPai);
        entity.setStatus(dto.status());
        entity.setFotoOvino(dto.fotoOvino());
        entity.setCompra(compra);
        entity.setParto(parto);
        entity.setPesagens(pesagens);
    }
}