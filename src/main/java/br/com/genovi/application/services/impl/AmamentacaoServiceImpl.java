package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.AmamentacaoService;
import br.com.genovi.domain.models.Amamentacao;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.dtos.amamentacao.AmamentacaoDTO;
import br.com.genovi.dtos.amamentacao.CreateAmamentacaoDTO;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.application.mapper.AmamentacaoMapper;
import br.com.genovi.infrastructure.repository.AmamentacaoRepository;
import br.com.genovi.infrastructure.repository.OvinoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AmamentacaoServiceImpl implements AmamentacaoService {

    private final AmamentacaoRepository amamentacaoRepository;
    private final OvinoRepository ovinoRepository;
    private final AmamentacaoMapper amamentacaoMapper;

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

        Amamentacao entity = new Amamentacao();
        entity.setOvelhaMae(ovinoMae);
        entity.setCordeiroMamando(cordeiro);
        entity.setDataInicio(dto.dataInicio());
        entity.setDataFim(dto.dataFim());
        entity.setObservacoes(dto.observacoes());

        amamentacaoRepository.save(entity);

        return amamentacaoMapper.toDTO(entity);
    }

    @Override
    public AmamentacaoDTO update(Long id, CreateAmamentacaoDTO dto) {
        Amamentacao entity = findAmamentacaoEntityById(id);
        Ovino ovinoMae = findOvinoEntityById(dto.ovelhaMaeId());
        Ovino cordeiro = findOvinoEntityById(dto.cordeiroMamandoId());

        entity.setOvelhaMae(ovinoMae);
        entity.setCordeiroMamando(cordeiro);
        entity.setDataInicio(dto.dataInicio());
        entity.setDataFim(dto.dataFim());
        entity.setObservacoes(dto.observacoes());

        amamentacaoRepository.save(entity);

        return amamentacaoMapper.toDTO(entity);
    }

    @Override
    public void delete(Long id) {
        findAmamentacaoEntityById(id);
        amamentacaoRepository.deleteById(id);
    }
}
