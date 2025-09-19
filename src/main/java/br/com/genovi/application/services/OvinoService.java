package br.com.genovi.application.services;

import br.com.genovi.domain.enums.TypeStatus;
import br.com.genovi.domain.models.*;
import br.com.genovi.dtos.ovino.CreateOvinoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import br.com.genovi.dtos.relatorios.GenealogiaDTO;
import br.com.genovi.infrastructure.mappers.OvinoMapper;
import br.com.genovi.infrastructure.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OvinoService {

    private final OvinoRepository ovinoRepository;
    private final AscendenciaRepository ascendenciaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final PartoRepository partoRepository;
    private final CompraRepository compraRepository;
    private final PesagemRepository pesagemRepository;
    private final OvinoMapper ovinoMapper;

    public OvinoService(OvinoRepository ovinoRepository, AscendenciaRepository ascendenciaRepository, FuncionarioRepository funcionarioRepository, PartoRepository partoRepository, CompraRepository compraRepository, PesagemRepository pesagemRepository, OvinoMapper ovinoMapper) {
        this.ovinoRepository = ovinoRepository;
        this.ascendenciaRepository = ascendenciaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.partoRepository = partoRepository;
        this.compraRepository = compraRepository;
        this.pesagemRepository = pesagemRepository;
        this.ovinoMapper = ovinoMapper;
    }

    private Ovino findOvinoEntityById(Long id) {
        if (id == null) return null;
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrado"));
    }

//    private Ascendencia findAscendenciaEntityById(Long id) {
//        return ascendenciaRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Ascendência não encontrada"));
//    }

    private Funcionario findFuncionarioById(Long id) {
        if (id == null) return null;
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));
    }

    private Compra findCompraById(Long id) {
        if (id == null) return null;
        return compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra não encontrado"));
    }

    private Parto findPartoById(Long id) {
        if (id == null) return null;
        return partoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parto não encontrado"));
    }

    private List<Pesagem> findPesagensByIds(List<Long> ids) {
        if (ids == null) return null;
        List<Pesagem> pesagens = pesagemRepository.findAllById(ids);
        if (pesagens.size() != ids.size()) {
            throw new RuntimeException("Alguma pesagem não foi encontrada");
        }
        return pesagens;
    }

    public List<OvinoDTO> findAll() {
        return ovinoRepository.findAll().stream()
                .map(ovinoMapper::toDTO)
                .toList();
    }

    public OvinoDTO findById(Long id) {
        return ovinoMapper.toDTO(findOvinoEntityById(id));
    }

    public OvinoDTO save(CreateOvinoDTO dto) {
        Ovino mae = findOvinoEntityById(dto.maeId());
        Ovino pai = findOvinoEntityById(dto.paiId());
        Compra compra = findCompraById(dto.compra());
        Parto parto = findPartoById(dto.parto());
        List<Pesagem> pesagens = findPesagensByIds(dto.pesos());

        Ovino ovino = ovinoMapper.toEntity(dto, mae, pai, compra, parto, pesagens);

        ovinoRepository.save(ovino);

        return ovinoMapper.toDTO(ovino);
    }

    public OvinoDTO update(Long id, CreateOvinoDTO dto) {
        Ovino entity = findOvinoEntityById(id);
        Ovino mae = findOvinoEntityById(dto.maeId());
        Ovino pai = findOvinoEntityById(dto.paiId());
        Compra compra = findCompraById(dto.compra());
        Parto parto = findPartoById(dto.parto());
        List<Pesagem> pesagens = findPesagensByIds(dto.pesos());

        ovinoMapper.updateEntityFromDTO(dto, entity, mae, pai, compra, parto, pesagens);

        ovinoRepository.save(entity);

        return ovinoMapper.toDTO(entity);
    }

    public void disable(Long id) {
        Ovino ovino = findOvinoEntityById(id);
        ovino.setStatus(TypeStatus.DESATIVADO);
        ovinoRepository.save(ovino);
    }
}
