package br.com.genovi.dtos.ovino;

import br.com.genovi.domain.enums.EnumGrauPureza;
import br.com.genovi.domain.enums.EnumRaca;
import br.com.genovi.domain.enums.EnumSexo;
import br.com.genovi.domain.enums.EnumStatus;

import java.time.LocalDateTime;
import java.util.List;

public record CreateOvinoDTO(Long rfid,
                             String nome,
                             EnumRaca raca,
                             String fbb,
                             LocalDateTime dataNascimento,
                             LocalDateTime dataCadastro,
                             EnumGrauPureza enumGrauPureza,
                             EnumSexo sexo,
                             Long maeId,
                             Long paiId,
                             EnumStatus status,
                             String fotoOvino,
                             Long compra,
                             Long parto,
                             List<Long> pesos) {
}
