package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.OcorrenciaDoencaService;
import br.com.genovi.domain.models.OcorrenciaDoenca;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Doenca;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.domain.utils.DateValidationUtils;
import br.com.genovi.dtos.ocorrencia_doenca.CreateOcorrenciaDoencaDTO;
import br.com.genovi.dtos.ocorrencia_doenca.OcorrenciaDoencaDTO;
import br.com.genovi.infrastructure.mappers.OcorrenciaDoencaMapper;
import br.com.genovi.infrastructure.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OcorrenciaDoencaServiceImpl implements OcorrenciaDoencaService {

    private final OcorrenciaDoencaRepository ocorrenciaDoencaRepository;
    private final OvinoRepository ovinoRepository;
    private final DoencaRepository doencaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final OcorrenciaDoencaMapper ocorrenciaDoencaMapper;

    public OcorrenciaDoencaServiceImpl(OcorrenciaDoencaRepository ocorrenciaDoencaRepository, OvinoRepository ovinoRepository, DoencaRepository doencaRepository, OcorrenciaDoencaMapper ocorrenciaDoencaMapper, FuncionarioRepository funcionarioRepository) {
        this.ocorrenciaDoencaRepository = ocorrenciaDoencaRepository;
        this.ovinoRepository = ovinoRepository;
        this.doencaRepository = doencaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.ocorrenciaDoencaMapper = ocorrenciaDoencaMapper;
    }

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
        DateValidationUtils.validarPeriodo(dto.dataInicio(), dto.dataFinal());

        Ovino ovino = findOvinoEntityById(dto.ovinoId());
        Doenca doenca = findDoencaEntityById(dto.doencaId());

        OcorrenciaDoenca ocorrenciaDoenca = ocorrenciaDoencaMapper.toEntity(dto, ovino, doenca);

        ocorrenciaDoencaRepository.save(ocorrenciaDoenca);

        return ocorrenciaDoencaMapper.toDTO(ocorrenciaDoenca);
    }

    @Override
    public OcorrenciaDoencaDTO update(Long id, CreateOcorrenciaDoencaDTO dto) {
        DateValidationUtils.validarPeriodo(dto.dataInicio(), dto.dataFinal());

        Ovino ovino = findOvinoEntityById(dto.ovinoId());
        Doenca doenca = findDoencaEntityById(dto.doencaId());

        OcorrenciaDoenca ocorrenciaDoenca = findOcorrenciaDoencaById(id);

        Long existingId = ocorrenciaDoenca.getId();
        OcorrenciaDoenca updatedOcorrenciaDoenca = ocorrenciaDoencaMapper.toEntity(dto, ovino, doenca);
        updatedOcorrenciaDoenca.setId(existingId);
        ocorrenciaDoencaRepository.save(updatedOcorrenciaDoenca);

        return ocorrenciaDoencaMapper.toDTO(updatedOcorrenciaDoenca);
    }

    @Override
    public void delete(Long id) {
        findOcorrenciaDoencaById(id);
        ocorrenciaDoencaRepository.deleteById(id);
    }
}
