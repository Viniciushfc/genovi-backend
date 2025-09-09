package br.com.genovi.dtos.criador;

public record CreateCriadorDTO(String nome,
                               String cpfCnpj,
                               String endereco,
                               String telefone) {
}
