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

    private Amamentacao findAmamentacaoEntityById(Long id) {
        return amamentacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Amamentação não encontrada"));
    }

    private Ovino findOvinoEntityById(Long id) {
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrado"));
    }

    public List<AmamentacaoDTO> findAll() {
        return amamentacaoRepository.findAll().stream().map(amamentacaoMapper::toDTO).toList();
    }

    public AmamentacaoDTO findById(Long id) {
        return amamentacaoMapper.toDTO(findAmamentacaoEntityById(id));
    }

    public AmamentacaoDTO save(CreateAmamentacaoDTO dto) {
        Ovino ovinoMae = findOvinoEntityById(dto.ovelhaMaeId());
        Ovino cordeiro = findOvinoEntityById(dto.cordeiroMamandoId());

        Amamentacao amamentacao = amamentacaoMapper.toEntity(dto, ovinoMae, cordeiro);
        
        amamentacaoRepository.save(amamentacao);

        return amamentacaoMapper.toDTO(amamentacao);
    }

    public AmamentacaoDTO update(Long id, CreateAmamentacaoDTO dto) {
        Amamentacao amamentacao = findAmamentacaoEntityById(id);
        Ovino ovinoMae = findOvinoEntityById(dto.ovelhaMaeId());
        Ovino cordeiro = findOvinoEntityById(dto.cordeiroMamandoId());

        amamentacaoMapper.updateEntityFromDTO(dto, amamentacao, ovinoMae, cordeiro);

        amamentacaoRepository.save(amamentacao);
        return amamentacaoMapper.toDTO(amamentacao);
    }

    public void delete(Long id) {
        findAmamentacaoEntityById(id);
        amamentacaoRepository.deleteById(id);
    }
}
