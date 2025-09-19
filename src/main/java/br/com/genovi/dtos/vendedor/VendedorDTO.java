package br.com.genovi.dtos.vendedor;

public record VendedorDTO(
        Long id,
        boolean ativo,
        String nome,
        String cpfCnpj,
        String email,
        String endereco,
        String telefone) {
}
