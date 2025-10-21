package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.RegistroService;
import br.com.genovi.domain.models.*;
import br.com.genovi.dtos.registro.CreateRegistroDTO;
import br.com.genovi.dtos.registro.RegistroDTO;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.infrastructure.mapper.RegistroMapper;
import br.com.genovi.infrastructure.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RegistroServiceImpl implements RegistroService {

    private final RegistroRepository registroRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ReproducaoRepository reproducaoRepository;
    private final GestacaoRepository gestacaoRepository;
    private final PartoRepository partoRepository;
    private final AplicacaoRepository aplicacaoRepository;
    private final OcorrenciaDoencaRepository ocorrenciaDoencaRepository;
    private final RegistroMapper registroMapper;

    private Registro findRegistroById(Long id) {
        if (id == null) return null;
        return registroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado"));
    }

    private Funcionario findFuncionarioById(Long id) {
        if (id == null) return null;
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));
    }

    private Reproducao findReproducaoById(Long id) {
        if (id == null) return null;
        return reproducaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reprodução não encontrada"));
    }

    private Gestacao findGestacaoById(Long id) {
        if (id == null) return null;
        return gestacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gestação não encontrada"));
    }

    private Parto findPartoById(Long id) {
        if (id == null) return null;
        return partoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parto não encontrado"));
    }

    private Aplicacao findAplicacaoById(Long id) {
        if (id == null) return null;
        return aplicacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aplicação não encontrada"));
    }

    private OcorrenciaDoenca findOcorrenciaDoencaById(Long id) {
        if (id == null) return null;
        return ocorrenciaDoencaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ocorrência de Doença não encontrada"));
    }

    @Override
    public List<RegistroDTO> findAll() {
        return registroRepository.findAll().stream().map(registroMapper::toDTO).toList();
    }

    @Override
    public RegistroDTO findById(Long id) {
        return registroMapper.toDTO(findRegistroById(id));
    }

    @Override
    public RegistroDTO save(CreateRegistroDTO dto) {
        Funcionario funcionario = findFuncionarioById(dto.idFuncionario());
        Reproducao reproducao = findReproducaoById(dto.idReproducao());
        Gestacao gestacao = findGestacaoById(dto.idGestacao());
        Parto parto = findPartoById(dto.idParto());
        Aplicacao aplicacao = findAplicacaoById(dto.idAplicacoes());
        OcorrenciaDoenca ocorrenciaDoenca = findOcorrenciaDoencaById(dto.idOcorrenciaDoencas());

        Registro registro = registroMapper.toEntity(dto, funcionario, reproducao, gestacao, parto, aplicacao, ocorrenciaDoenca);

        return registroMapper.toDTO(registroRepository.save(registro));
    }

    @Override
    public RegistroDTO update(Long id, CreateRegistroDTO dto) {
        Registro entity = findRegistroById(id);
        Funcionario funcionario = findFuncionarioById(dto.idFuncionario());
        Reproducao reproducao = findReproducaoById(dto.idReproducao());
        Gestacao gestacao = findGestacaoById(dto.idGestacao());
        Parto parto = findPartoById(dto.idParto());
        Aplicacao aplicacao = findAplicacaoById(dto.idAplicacoes());
        OcorrenciaDoenca ocorrenciaDoenca = findOcorrenciaDoencaById(dto.idOcorrenciaDoencas());

        entity.setDataRegistro(dto.dataRegistro());
        entity.setFuncionario(funcionario);
        entity.setReproducao(reproducao);
        entity.setGestacao(gestacao);
        entity.setParto(parto);
        entity.setAplicacao(aplicacao);
        entity.setOcorrenciaDoenca(ocorrenciaDoenca);

        return registroMapper.toDTO(registroRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        findRegistroById(id);
        registroRepository.deleteById(id);
    }

    @Override
    public RegistroDTO sugestaoParaRegistro(Long id, Boolean isSugestao) {
        Registro registro = findRegistroById(id);
        registro.setIsSugestao(isSugestao);
        registroRepository.save(registro);
        return registroMapper.toDTO(registro);
    }
}
