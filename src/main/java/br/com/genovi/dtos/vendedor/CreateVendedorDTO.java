package br.com.genovi.dtos.vendedor;

public record CreateVendedorDTO(
        String nome,
        boolean ativo,
        String cpfCnpj,
        String email,
        String endereco,
        String telefone
) {
}
