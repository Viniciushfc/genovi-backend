package br.com.genovi.dtos.ovino;

import br.com.genovi.domain.enums.TypeGrauPureza;
import br.com.genovi.domain.enums.TypeRaca;
import br.com.genovi.domain.enums.TypeSexo;
import br.com.genovi.domain.enums.TypeStatus;

import java.time.LocalDateTime;
import java.util.List;

public record CreateOvinoDTO(Long rfid,
                             String nome,
                             TypeRaca raca,
                             String fbb,
                             LocalDateTime dataNascimento,
                             LocalDateTime dataCadastro,
                             TypeGrauPureza typeGrauPureza,
                             TypeSexo sexo,
                             Long maeId,
                             Long paiId,
                             TypeStatus status,
                             String fotoOvino,
                             Long compra,
                             Long parto,
                             List<Long> pesos) {
}
