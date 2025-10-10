package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.AmamentacaoService;
import br.com.genovi.domain.models.Amamentacao;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.amamentacao.AmamentacaoDTO;
import br.com.genovi.dtos.amamentacao.CreateAmamentacaoDTO;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.infrastructure.mappers.AmamentacaoMapper;
import br.com.genovi.infrastructure.repositories.AmamentacaoRepository;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmamentacaoServiceImpl implements AmamentacaoService {

    private final AmamentacaoRepository amamentacaoRepository;
    private final OvinoRepository ovinoRepository;
    private final AmamentacaoMapper amamentacaoMapper;

    public AmamentacaoServiceImpl(AmamentacaoRepository amamentacaoRepository, OvinoRepository ovinoRepository, AmamentacaoMapper amamentacaoMapper) {
        this.amamentacaoRepository = amamentacaoRepository;
        this.ovinoRepository = ovinoRepository;
        this.amamentacaoMapper = amamentacaoMapper;
    }

    private Amamentacao findAmamentacaoEntityById(Long id) {
        if (id == null) return null;
        return amamentacaoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Amamentação não encontrada"));
    }
    private Ovino findOvinoEntityById(Long id) {
        if (id == null) return null;
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ovino não encontrado"));
    }

    @Override
    public List<AmamentacaoDTO> findAll() {
        return amamentacaoRepository.findAll().stream().map(amamentacaoMapper::toDTO).toList();
    }

    @Override
    public AmamentacaoDTO findById(Long id) {
        return amamentacaoMapper.toDTO(findAmamentacaoEntityById(id));
    }

    @Override
    public AmamentacaoDTO save(CreateAmamentacaoDTO dto) {
        Ovino ovinoMae = findOvinoEntityById(dto.ovelhaMaeId());
        Ovino cordeiro = findOvinoEntityById(dto.cordeiroMamandoId());

        Amamentacao amamentacao = amamentacaoMapper.toEntity(dto, ovinoMae, cordeiro);

        amamentacaoRepository.save(amamentacao);

        return amamentacaoMapper.toDTO(amamentacao);
    }

    @Override
    public AmamentacaoDTO update(Long id, CreateAmamentacaoDTO dto) {
        Amamentacao amamentacao = findAmamentacaoEntityById(id);
        Ovino ovinoMae = findOvinoEntityById(dto.ovelhaMaeId());
        Ovino cordeiro = findOvinoEntityById(dto.cordeiroMamandoId());

        Long existingId = amamentacao.getId();
        Amamentacao updatedAmamentacao = amamentacaoMapper.toEntity(dto, ovinoMae, cordeiro);
        updatedAmamentacao.setId(existingId);
        amamentacaoRepository.save(updatedAmamentacao);

        return amamentacaoMapper.toDTO(updatedAmamentacao);
    }

    @Override
    public void delete(Long id) {
        findAmamentacaoEntityById(id);
        amamentacaoRepository.deleteById(id);
    }
}
