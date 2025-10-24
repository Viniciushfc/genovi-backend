package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.OcorrenciaDoencaService;
import br.com.genovi.domain.models.OcorrenciaDoenca;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Doenca;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.dtos.ocorrencia_doenca.CreateOcorrenciaDoencaDTO;
import br.com.genovi.dtos.ocorrencia_doenca.OcorrenciaDoencaDTO;
import br.com.genovi.application.mapper.OcorrenciaDoencaMapper;
import br.com.genovi.infrastructure.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OcorrenciaDoencaServiceImpl implements OcorrenciaDoencaService {

    private final OcorrenciaDoencaRepository ocorrenciaDoencaRepository;
    private final OvinoRepository ovinoRepository;
    private final DoencaRepository doencaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final OcorrenciaDoencaMapper ocorrenciaDoencaMapper;

    private OcorrenciaDoenca findOcorrenciaDoencaById(Long id) {
        if (id == null) return null;
        return ocorrenciaDoencaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("OcorrenciaDoenca não encontrado"));
    }

    private Ovino findOvinoEntityById(Long id) {
        if (id == null) return null;
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ovino não encontrado para Ocorrencia Doença"));
    }

    private Doenca findDoencaEntityById(Long id) {
        if (id == null) return null;
        return doencaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doença não encontrada para Ocorrencia Doença"));
    }

    @Override
    public List<OcorrenciaDoencaDTO> findAll() {
        return ocorrenciaDoencaRepository.findAll().stream().map(ocorrenciaDoencaMapper::toDTO).toList();
    }

    @Override
    public OcorrenciaDoencaDTO findById(Long id) {
        return ocorrenciaDoencaMapper.toDTO(findOcorrenciaDoencaById(id));
    }

    @Override
    public OcorrenciaDoencaDTO save(CreateOcorrenciaDoencaDTO dto) {
        Ovino ovino = findOvinoEntityById(dto.ovinoId());
        Doenca doenca = findDoencaEntityById(dto.doencaId());

        OcorrenciaDoenca ocorrenciaDoenca = ocorrenciaDoencaMapper.toEntity(dto, ovino, doenca);

        ocorrenciaDoencaRepository.save(ocorrenciaDoenca);

        return ocorrenciaDoencaMapper.toDTO(ocorrenciaDoenca);
    }

    @Override
    public OcorrenciaDoencaDTO update(Long id, CreateOcorrenciaDoencaDTO dto) {
        OcorrenciaDoenca entity = findOcorrenciaDoencaById(id);
        Ovino ovino = findOvinoEntityById(dto.ovinoId());
        Doenca doenca = findDoencaEntityById(dto.doencaId());

        entity.setOvino(ovino);
        entity.setDoenca(doenca);
        entity.setDataInicio(dto.dataInicio());
        entity.setDataFinal(dto.dataFinal());
        entity.setCurada(dto.curado());

        return ocorrenciaDoencaMapper.toDTO(ocorrenciaDoencaRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        findOcorrenciaDoencaById(id);
        ocorrenciaDoencaRepository.deleteById(id);
    }
}
