package br.com.genovi.application.mapper;

import br.com.genovi.domain.models.Compra;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Parto;
import br.com.genovi.domain.models.Pesagem;
import br.com.genovi.dtos.ovino.CreateOvinoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import br.com.genovi.dtos.ovino.OvinoResumeDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class OvinoMapper {

    private final PartoMapper partoMapper;
    private final PesagemMapper pesagemMapper;
    private final CompraMapper compraMapper;

    public Ovino toEntity(CreateOvinoDTO dto, Ovino ovinoMae, Ovino ovinoPai, Compra compra, Parto parto, List<Pesagem> pesagens) {
        LocalDateTime dataCadastro;
        LocalDateTime dataNascimento;

        if (parto != null && parto.getDataParto() != null) {
            dataCadastro = parto.getDataParto();
            dataNascimento = parto.getDataParto();
        } else {
            dataCadastro = (compra != null && compra.getDataCompra() != null) ? compra.getDataCompra() : LocalDateTime.now();

            if (dto.dataNascimento() == null) {
                throw new IllegalArgumentException("A data de nascimento é obrigatória quando o ovino não é de um parto registrado.");
            }
            dataNascimento = dto.dataNascimento();
        }

        return new Ovino(
                null,
                dto.rfid(),
                dto.nome(),
                dto.raca(),
                dto.fbb(),
                dataNascimento,
                dataCadastro,
                dto.grauPureza(),
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
                entity.getEnumGrauPureza(),
                entity.getSexo(),
                toResumoDTO(entity.getOvinoMae()),
                toResumoDTO(entity.getOvinoPai()),
                entity.getStatus(),
                entity.getFotoOvino(),
                compraMapper.toDTO(entity.getCompra()),
                partoMapper.toDTO(entity.getParto()),
                entity.getPesagens() == null
                        ? Collections.emptyList()
                        : entity.getPesagens().stream().map(pesagemMapper::toDTO).toList()
        );
    }

    public OvinoResumeDTO toResumoDTO(Ovino entity) {
        if (entity == null) {
            return null;
        }

        return new OvinoResumeDTO(
                entity.getId(),
                entity.getRfid(),
                entity.getNome(),
                entity.getFbb()
        );
    }
}