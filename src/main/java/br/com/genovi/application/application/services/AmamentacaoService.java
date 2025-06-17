package br.com.genovi.application.application.services;

import br.com.genovi.application.domain.models.Amamentacao;
import br.com.genovi.application.domain.models.Ovino;
import br.com.genovi.application.dtos.AmamentacaoDTO;
import br.com.genovi.application.dtos.CreateAmamentacaoDTO;
import br.com.genovi.application.infrastructure.mappers.AmamentacaoMapper;
import br.com.genovi.application.infrastructure.repositories.AmamentacaoRepository;
import br.com.genovi.application.infrastructure.repositories.OvinoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmamentacaoService {

    private final AmamentacaoRepository amamentacaoRepository;
    private final OvinoRepository ovinoRepository;
    private final AmamentacaoMapper amamentacaoMapper;

    public AmamentacaoService(AmamentacaoRepository amamentacaoRepository, OvinoRepository ovinoRepository, AmamentacaoMapper amamentacaoMapper) {
        this.amamentacaoRepository = amamentacaoRepository;
        this.ovinoRepository = ovinoRepository;
        this.amamentacaoMapper = amamentacaoMapper;
    }

    public Amamentacao findAmamentacaoEntityById(Long id) {
        return amamentacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Amamentação não encontrada"));
    }

    public List<AmamentacaoDTO> findAll() {
        return amamentacaoRepository.findAll().stream().map(amamentacaoMapper::toDTO).toList();
    }

    public AmamentacaoDTO findById(Long id) {
        Amamentacao amamentacao = amamentacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Amamentação não encontrada"));
        return amamentacaoMapper.toDTO(amamentacao);
    }

    public AmamentacaoDTO save(CreateAmamentacaoDTO dto) {
        Ovino ovinoMae = ovinoRepository.findById(dto.ovelhaMaeId()).orElseThrow(() -> new RuntimeException("Ovelha Mãe não encontrada"));
        Ovino cordeiro = ovinoRepository.findById(dto.cordeiroMamandoId()).orElseThrow(() -> new RuntimeException("Ovelha Mãe não encontrada"));

        Amamentacao amamentacao = amamentacaoMapper.toEntity(dto);
        amamentacao.setOvelhaMae(ovinoMae);
        amamentacao.setCordeiroMamando(cordeiro);
        amamentacaoRepository.save(amamentacao);

        return amamentacaoMapper.toDTO(amamentacao);
    }

    public AmamentacaoDTO update(Long id, CreateAmamentacaoDTO dto) {
        Amamentacao amamentacao = findAmamentacaoEntityById(id);
        Ovino ovinoMae = ovinoRepository.findById(dto.ovelhaMaeId()).orElseThrow(() -> new RuntimeException("Ovelha Mãe não encontrada"));
        Ovino cordeiro = ovinoRepository.findById(dto.cordeiroMamandoId()).orElseThrow(() -> new RuntimeException("Ovelha Mãe não encontrada"));

        amamentacaoMapper.updateEntityFromDTO(dto, amamentacao);
        amamentacao.setOvelhaMae(ovinoMae);
        amamentacao.setCordeiroMamando(cordeiro);

        amamentacaoRepository.save(amamentacao);
        return amamentacaoMapper.toDTO(amamentacao);
    }

    public void delete(Long id) {
        findAmamentacaoEntityById(id);
        amamentacaoRepository.deleteById(id);
    }
}
