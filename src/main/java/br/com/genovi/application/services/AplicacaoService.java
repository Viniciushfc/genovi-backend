package br.com.genovi.application.services;

import br.com.genovi.domain.models.Aplicacao;
import br.com.genovi.domain.models.Medicamento;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.dtos.aplicacao.AplicacaoDTO;
import br.com.genovi.dtos.aplicacao.CreateAplicacaoDTO;
import br.com.genovi.infrastructure.mappers.AplicacaoMapper;
import br.com.genovi.infrastructure.repositories.AplicacaoRepository;
import br.com.genovi.infrastructure.repositories.MedicamentoRepository;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import br.com.genovi.infrastructure.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AplicacaoService {

    private final AplicacaoRepository aplicacaoRepository;
    private final OvinoRepository ovinoRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final AplicacaoMapper aplicacaoMapper;

    public AplicacaoService(
            AplicacaoRepository aplicacaoRepository,
            OvinoRepository ovinoRepository,
            MedicamentoRepository medicamentoRepository,
            AplicacaoMapper aplicacaoMapper
    ) {
        this.aplicacaoRepository = aplicacaoRepository;
        this.ovinoRepository = ovinoRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.aplicacaoMapper = aplicacaoMapper;
    }

    private Ovino findOvinoById(Long id) {
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrado"));
    }

    private Medicamento findMedicamentoById(Long id) {
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));
    }

    private Aplicacao findAplicacaoById(Long id) {
        return aplicacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aplicacao não encontrada"));
    }

    public List<AplicacaoDTO> findAll() {
        return aplicacaoRepository.findAll().stream()
                .map(aplicacaoMapper::toDTO)
                .toList();
    }

    public AplicacaoDTO findById(Long id) {
        return aplicacaoMapper.toDTO(findAplicacaoById(id));
    }

    public AplicacaoDTO save(CreateAplicacaoDTO dto) {
        Ovino ovino = findOvinoById(dto.ovinoId());
        Medicamento medicamento = findMedicamentoById(dto.medicamentoId());

        Aplicacao aplicacao = aplicacaoMapper.toEntity(dto, ovino, medicamento);
        aplicacaoRepository.save(aplicacao);
        return aplicacaoMapper.toDTO(aplicacao);
    }

    public void delete(Long id) {
        Aplicacao aplicacao = findAplicacaoById(id);
        aplicacaoRepository.deleteById(aplicacao.getId());
    }
}
