package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.AplicacaoService;
import br.com.genovi.domain.models.Aplicacao;
import br.com.genovi.domain.models.Medicamento;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.aplicacao.AplicacaoDTO;
import br.com.genovi.dtos.aplicacao.CreateAplicacaoDTO;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.application.mapper.AplicacaoMapper;
import br.com.genovi.infrastructure.repository.AplicacaoRepository;
import br.com.genovi.infrastructure.repository.MedicamentoRepository;
import br.com.genovi.infrastructure.repository.OvinoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AplicacaoServiceImpl implements AplicacaoService {

    private final AplicacaoRepository aplicacaoRepository;
    private final OvinoRepository ovinoRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final AplicacaoMapper aplicacaoMapper;

    private Ovino findOvinoById(Long id) {
        if (id == null) return null;
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ovino não encontrado"));
    }

    private Medicamento findMedicamentoById(Long id) {
        if (id == null) return null;
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento não encontrado"));
    }

    private Aplicacao findAplicacaoById(Long id) {
        if (id == null) return null;
        return aplicacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aplicacao não encontrada"));
    }

    @Override
    public List<AplicacaoDTO> findAll() {
        return aplicacaoRepository.findAll().stream()
                .map(aplicacaoMapper::toDTO)
                .toList();
    }

    @Override
    public AplicacaoDTO findById(Long id) {
        return aplicacaoMapper.toDTO(findAplicacaoById(id));
    }

    @Override
    public AplicacaoDTO save(CreateAplicacaoDTO dto) {
        Ovino ovino = findOvinoById(dto.ovinoId());
        Medicamento medicamento = findMedicamentoById(dto.medicamentoId());

        Aplicacao aplicacao = aplicacaoMapper.toEntity(dto, ovino, medicamento);
        aplicacaoRepository.save(aplicacao);
        return aplicacaoMapper.toDTO(aplicacao);
    }

    @Override
    public AplicacaoDTO update(Long id, CreateAplicacaoDTO dto) {
        Aplicacao entity = findAplicacaoById(id);
        Ovino ovino = findOvinoById(dto.ovinoId());
        Medicamento medicamento = findMedicamentoById(dto.medicamentoId());

        entity.setOvino(ovino);
        entity.setMedicamento(medicamento);
        entity.setDataAplicacao(dto.dataAplicacao());
        entity.setDataProximaDose(dto.dataProximaDose());

        return aplicacaoMapper.toDTO(aplicacaoRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        Aplicacao aplicacao = findAplicacaoById(id);
        aplicacaoRepository.deleteById(aplicacao.getId());
    }
}
