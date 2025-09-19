package br.com.genovi.dtos.funcionario;

import java.time.LocalDateTime;

public record FuncionarioDTO(
        Long id,
        String nome,
        String cpfCnpj,
        String endereco,
        String telefone,
        LocalDateTime dataAdmissao,
        LocalDateTime dataRecisao) {
}
