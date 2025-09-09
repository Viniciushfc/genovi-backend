package br.com.genovi.dtos.criador;

public record CriadorDTO(Long id,
                         String nome,
                         String cpfCnpj,
                         String endereco,
                         String telefone) {
}
