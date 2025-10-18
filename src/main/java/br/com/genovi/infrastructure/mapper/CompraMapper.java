package br.com.genovi.infrastructure.mapper;

import br.com.genovi.domain.models.Compra;
import br.com.genovi.domain.models.Vendedor;
import br.com.genovi.dtos.compra.CompraDTO;
import br.com.genovi.dtos.compra.CreateCompraDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CompraMapper {

    private final VendedorMapper vendedorMapper;

    public Compra toEntity(CreateCompraDTO dto, Vendedor vendedor) {
        return new Compra(
                null,
                dto.dataCompra(),
                dto.valor(),
                vendedor
        );
    }

    public CompraDTO toDTO(Compra entity) {
        if (entity == null) {
            return null;
        }
        return new CompraDTO(
                entity.getId(),
                entity.getDataCompra(),
                entity.getValor(),
                vendedorMapper.toDTO(entity.getVendedor())
        );
    }
}
