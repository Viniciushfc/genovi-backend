package br.com.genovi.application.services;

import br.com.genovi.domain.models.Aplicacao;
import br.com.genovi.domain.models.Medicamento;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.domain.utils.DateValidationUtils;
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
    private final UsuarioRepository usuarioRepository;
    private final AplicacaoMapper aplicacaoMapper;

    public AplicacaoService(AplicacaoRepository aplicacaoRepository, OvinoRepository ovinoRepository, MedicamentoRepository medicamentoRepository, UsuarioRepository usuarioRepository, AplicacaoMapper aplicacaoMapper) {
        this.aplicacaoRepository = aplicacaoRepository;
        this.ovinoRepository = ovinoRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.aplicacaoMapper = aplicacaoMapper;
    }

    private Aplicacao findAplicacaoById(Long id) {
        return aplicacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Aplicação não encontrada"));
    }

    private Ovino findOvinoById(Long id) {
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrado para Aplicação"));
    }

    private Medicamento findMedicamentoById(Long id) {
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado para Aplicação"));
    }

    private Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para Aplicação"));
    }

    public List<AplicacaoDTO> findAll() {
        return aplicacaoRepository.findAll().stream().map(aplicacaoMapper::toDTO).toList();
    }

    public AplicacaoDTO findById(Long id) {
        return aplicacaoMapper.toDTO(findAplicacaoById(id));
    }

    public AplicacaoDTO save(CreateAplicacaoDTO dto) {
        DateValidationUtils.validarPeriodo(dto.dataAplicacao(), dto.dataProximaDose());

        Ovino ovino = findOvinoById(dto.ovinoId());
        Medicamento medicamento = findMedicamentoById(dto.medicamentoId());
        Usuario usuario = findUsuarioById(dto.responsavelId());

        Aplicacao aplicacao = aplicacaoMapper.toEntity(dto, ovino, medicamento, usuario);

        aplicacaoRepository.save(aplicacao);

        return aplicacaoMapper.toDTO(aplicacao);
    }

    public AplicacaoDTO update(Long id, CreateAplicacaoDTO dto) {
        DateValidationUtils.validarPeriodo(dto.dataAplicacao(), dto.dataProximaDose());
        
        Aplicacao aplicacao = findAplicacaoById(id);
        Ovino ovino = findOvinoById(dto.ovinoId());
        Medicamento medicamento = findMedicamentoById(dto.medicamentoId());
        Usuario usuario = findUsuarioById(dto.responsavelId());

        aplicacaoMapper.updateEntityFromDTO(dto, aplicacao, ovino, medicamento, usuario);

        aplicacaoRepository.save(aplicacao);

        return aplicacaoMapper.toDTO(aplicacao);
    }

    public void delete(Long id) {
        findAplicacaoById(id);
        aplicacaoRepository.deleteById(id);
    }
}
