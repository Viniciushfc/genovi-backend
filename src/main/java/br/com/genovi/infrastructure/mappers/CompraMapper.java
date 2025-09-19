package br.com.genovi.infrastructure.mappers;

import br.com.genovi.domain.models.Compra;
import br.com.genovi.domain.models.Vendedor;
import br.com.genovi.dtos.compra.CompraDTO;
import br.com.genovi.dtos.compra.CreateCompraDTO;
import org.springframework.stereotype.Component;

@Component
public class CompraMapper {

    private final VendedorMapper vendedorMapper;

    public CompraMapper(VendedorMapper vendedorMapper) {
        this.vendedorMapper = vendedorMapper;
    }

    public Compra toEntity(CreateCompraDTO dto, Vendedor vendedor) {
        return new Compra(
                null,
                dto.dataCompra(),
                dto.valor(),
                vendedor
        );
    }

    public CompraDTO toDTO(Compra compra) {
        if (compra == null) {
            return null;
        }
        return new CompraDTO(
                compra.getId(),
                compra.getDataCompra(),
                compra.getValor(),
                vendedorMapper.toDTO(compra.getVendedor())
        );
    }


    public void updateEntityFromDTO(CreateCompraDTO dto, Compra entity, Vendedor vendedor) {
        entity.setDataCompra(dto.dataCompra());
        entity.setValor(dto.valor());
        entity.setVendedor(vendedor);
    }
}
