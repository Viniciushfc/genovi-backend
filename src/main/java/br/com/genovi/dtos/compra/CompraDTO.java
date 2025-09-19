package br.com.genovi.dtos.compra;

import br.com.genovi.dtos.vendedor.VendedorDTO;

import java.time.LocalDateTime;

public record CompraDTO(
        Long id,
        LocalDateTime dataCompra,
        Double valor,
        VendedorDTO vendedor
) {
}
