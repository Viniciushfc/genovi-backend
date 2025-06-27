package br.com.genovi.application.services;

import br.com.genovi.domain.models.Doenca;
import br.com.genovi.domain.models.OcorrenciaDoenca;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Usuario;
import br.com.genovi.dtos.ocorrencia_doenca.CreateOcorrenciaDoencaDTO;
import br.com.genovi.dtos.ocorrencia_doenca.OcorrenciaDoencaDTO;
import br.com.genovi.infrastructure.mappers.OcorrenciaDoencaMapper;
import br.com.genovi.infrastructure.repositories.DoencaRepository;
import br.com.genovi.infrastructure.repositories.OcorrenciaDoencaRepository;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import br.com.genovi.infrastructure.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OcorrenciaDoencaService {

    private final OcorrenciaDoencaRepository ocorrenciaDoencaRepository;
    private final OvinoRepository ovinoRepository;
    private final DoencaRepository doencaRepository;
    private final UsuarioRepository usuarioRepository;
    private final OcorrenciaDoencaMapper ocorrenciaDoencaMapper;

    public OcorrenciaDoencaService(OcorrenciaDoencaRepository ocorrenciaDoencaRepository, OvinoRepository ovinoRepository, DoencaRepository doencaRepository, UsuarioRepository usuarioRepository, OcorrenciaDoencaMapper ocorrenciaDoencaMapper) {
        this.ocorrenciaDoencaRepository = ocorrenciaDoencaRepository;
        this.ovinoRepository = ovinoRepository;
        this.doencaRepository = doencaRepository;
        this.usuarioRepository = usuarioRepository;
        this.ocorrenciaDoencaMapper = ocorrenciaDoencaMapper;
    }

    private OcorrenciaDoenca findOcorrenciaDoencaById(Long id) {
        return ocorrenciaDoencaRepository.findById(id).orElseThrow(() -> new RuntimeException("OcorrenciaDoenca não encontrado"));
    }

    private Ovino findOvinoEntityById(Long id) {
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrado para Ocorrencia Doença"));
    }

    private Doenca findDoencaEntityById(Long id) {
        return doencaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doença não encontrada para Ocorrencia Doença"));
    }

    private Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para Ocorrencia Doença"));
    }

    public List<OcorrenciaDoencaDTO> findAll() {
        return ocorrenciaDoencaRepository.findAll().stream().map(ocorrenciaDoencaMapper::toDTO).toList();
    }

    public OcorrenciaDoencaDTO findById(Long id) {
        return ocorrenciaDoencaMapper.toDTO(findOcorrenciaDoencaById(id));
    }

    public OcorrenciaDoencaDTO save(CreateOcorrenciaDoencaDTO dto) {
        Ovino ovino = findOvinoEntityById(dto.ovinoId());
        Doenca doenca = findDoencaEntityById(dto.doencaId());
        Usuario usuario = findUsuarioById(dto.responsavelId());

        OcorrenciaDoenca ocorrenciaDoenca = ocorrenciaDoencaMapper.toEntity(dto, ovino, doenca, usuario);

        ocorrenciaDoencaRepository.save(ocorrenciaDoenca);

        return ocorrenciaDoencaMapper.toDTO(ocorrenciaDoenca);
    }

    public OcorrenciaDoencaDTO update(Long id, CreateOcorrenciaDoencaDTO dto) {
        Ovino ovino = findOvinoEntityById(dto.ovinoId());
        Doenca doenca = findDoencaEntityById(dto.doencaId());
        Usuario usuario = findUsuarioById(dto.responsavelId());

        OcorrenciaDoenca ocorrenciaDoenca = findOcorrenciaDoencaById(id);
        ocorrenciaDoencaMapper.updateEntityFromDTO(dto, ocorrenciaDoenca, ovino, doenca, usuario);

        ocorrenciaDoencaRepository.save(ocorrenciaDoenca);

        return ocorrenciaDoencaMapper.toDTO(ocorrenciaDoenca);
    }

    public void delete(Long id) {
        findOcorrenciaDoencaById(id);
        ocorrenciaDoencaRepository.deleteById(id);
    }
}
