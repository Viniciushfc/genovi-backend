package br.com.genovi.dtos.ovino;

import br.com.genovi.domain.enums.TypeGrauPureza;
import br.com.genovi.domain.enums.TypeRaca;
import br.com.genovi.domain.enums.TypeSexo;
import br.com.genovi.domain.enums.TypeStatus;
import br.com.genovi.domain.models.Compra;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Parto;
import br.com.genovi.dtos.ascendencia.AscendenciaDTO;
import br.com.genovi.dtos.compra.CompraDTO;
import br.com.genovi.dtos.funcionario.FuncionarioDTO;
import br.com.genovi.dtos.parto.PartoDTO;
import br.com.genovi.dtos.pesagem.PesagemDTO;

import java.time.LocalDateTime;
import java.util.List;

public record OvinoDTO(
        Long id,
        Long rfid,
        String nome,
        TypeRaca raca,
        String fbb,
        LocalDateTime dataNascimento,
        LocalDateTime dataCadastro,
        TypeGrauPureza typeGrauPureza,
        TypeSexo sexo,
        Ovino maeOvino,
        Ovino paiOvino,
        TypeStatus status,
        String fotoOvino,
        CompraDTO compra,
        PartoDTO parto,
        List<PesagemDTO> pesos){
}
