package br.com.genovi.dtos.compra;

import java.time.LocalDateTime;

public record CreateCompraDTO(LocalDateTime dataCompra,
                              Double valor,
                              Long vendedorId) {
}
