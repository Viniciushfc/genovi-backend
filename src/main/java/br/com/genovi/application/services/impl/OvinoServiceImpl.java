package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.OvinoService;
import br.com.genovi.domain.enums.EnumStatus;
import br.com.genovi.domain.models.*;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.dtos.ovino.CreateOvinoDTO;
import br.com.genovi.dtos.ovino.OvinoDTO;
import br.com.genovi.application.mapper.OvinoMapper;
import br.com.genovi.infrastructure.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OvinoServiceImpl implements OvinoService {

    private final OvinoRepository ovinoRepository;
    private final AscendenciaRepository ascendenciaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final PartoRepository partoRepository;
    private final CompraRepository compraRepository;
    private final PesagemRepository pesagemRepository;
    private final OvinoMapper ovinoMapper;

    private Ovino findOvinoEntityById(Long id) {
        if (id == null) return null;
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ovino não encontrado"));
    }

//    private Ascendencia findAscendenciaEntityById(Long id) {
//        return ascendenciaRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Ascendência não encontrada"));
//    }

    private Funcionario findFuncionarioById(Long id) {
        if (id == null) return null;
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario não encontrado"));
    }

    private Compra findCompraById(Long id) {
        if (id == null) return null;
        return compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra não encontrado"));
    }

    private Parto findPartoById(Long id) {
        if (id == null) return null;
        return partoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parto não encontrado"));
    }

    private List<Pesagem> findPesagensByIds(List<Long> ids) {
        if (ids == null) return null;
        List<Pesagem> pesagens = pesagemRepository.findAllById(ids);
        if (pesagens.size() != ids.size()) {
            throw new ResourceNotFoundException("Alguma pesagem não foi encontrada");
        }
        return pesagens;
    }

    @Override
    public List<OvinoDTO> findAll() {
        return ovinoRepository.findAll().stream()
                .map(ovinoMapper::toDTO)
                .toList();
    }

    @Override
    public OvinoDTO findById(Long id) {
        return ovinoMapper.toDTO(findOvinoEntityById(id));
    }

    @Override
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

    @Override
    public OvinoDTO update(Long id, CreateOvinoDTO dto) {
        Ovino entity = findOvinoEntityById(id);
        Ovino mae = findOvinoEntityById(dto.maeId());
        Ovino pai = findOvinoEntityById(dto.paiId());
        Compra compra = findCompraById(dto.compra());
        Parto parto = findPartoById(dto.parto());
        List<Pesagem> pesagens = findPesagensByIds(dto.pesos());

        entity.setRfid(dto.rfid());
        entity.setNome(dto.nome());
        entity.setRaca(dto.raca());
        entity.setFbb(dto.fbb());
        entity.setDataNascimento(dto.dataNascimento());
        entity.setDataCadastro(dto.dataCadastro());
        entity.setEnumGrauPureza(dto.enumGrauPureza());
        entity.setSexo(dto.sexo());
        entity.setOvinoMae(mae);
        entity.setOvinoPai(pai);
        entity.setStatus(dto.status());
        entity.setFotoOvino(dto.fotoOvino());
        entity.setCompra(compra);
        entity.setParto(parto);
        entity.setPesagens(pesagens);

        return ovinoMapper.toDTO(ovinoRepository.save(entity));
    }

    @Override
    public void disable(Long id) {
        Ovino ovino = findOvinoEntityById(id);
        ovino.setStatus(EnumStatus.DESATIVADO);
        ovinoRepository.save(ovino);
    }
}
