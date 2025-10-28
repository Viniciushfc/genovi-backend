package br.com.genovi.dtos.ovino;

import br.com.genovi.domain.enums.EnumGrauPureza;
import br.com.genovi.domain.enums.EnumRaca;
import br.com.genovi.domain.enums.EnumSexo;
import br.com.genovi.domain.enums.EnumStatus;
import br.com.genovi.dtos.compra.CompraDTO;
import br.com.genovi.dtos.parto.PartoDTO;
import br.com.genovi.dtos.pesagem.PesagemDTO;

import java.time.LocalDateTime;
import java.util.List;

public record OvinoDTO(
        Long id,
        Long rfid,
        String nome,
        EnumRaca raca,
        String fbb,
        LocalDateTime dataNascimento,
        LocalDateTime dataCadastro,
        EnumGrauPureza grauPureza,
        EnumSexo sexo,
        OvinoResumeDTO maeOvino,
        OvinoResumeDTO paiOvino,
        EnumStatus status,
        String fotoOvino,
        CompraDTO compra,
        PartoDTO parto,
        List<PesagemDTO> pesos){
}
