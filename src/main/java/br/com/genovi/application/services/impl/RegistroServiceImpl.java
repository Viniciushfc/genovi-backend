package br.com.genovi.application.services.impl;

import br.com.genovi.application.mapper.PesagemMapper;
import br.com.genovi.application.services.RegistroService;
import br.com.genovi.domain.models.*;
import br.com.genovi.dtos.registro.CreateRegistroDTO;
import br.com.genovi.dtos.registro.RegistroDTO;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.application.mapper.RegistroMapper;
import br.com.genovi.infrastructure.repository.*;
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
    private final PesagemRepository pesagemRepository;
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

    private Pesagem findPesagemById(Long id) {
        if (id == null) return null;
        return pesagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pesagem não encontrada"));
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
        Pesagem pesagem = findPesagemById(dto.idPessagem());

        Registro entity = new Registro();
        entity.setDataRegistro(dto.dataRegistro());
        entity.setIsSugestao(dto.isSugestao());
        entity.setFuncionario(funcionario);
        entity.setReproducao(reproducao);
        entity.setGestacao(gestacao);
        entity.setParto(parto);
        entity.setAplicacao(aplicacao);
        entity.setOcorrenciaDoenca(ocorrenciaDoenca);
        entity.setPesagem(pesagem);

        registroRepository.save(entity);

        return registroMapper.toDTO(entity);
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
        Pesagem pesagem = findPesagemById(dto.idPessagem());

        entity.setDataRegistro(dto.dataRegistro());
        entity.setIsSugestao(dto.isSugestao());
        entity.setFuncionario(funcionario);
        entity.setReproducao(reproducao);
        entity.setGestacao(gestacao);
        entity.setParto(parto);
        entity.setAplicacao(aplicacao);
        entity.setOcorrenciaDoenca(ocorrenciaDoenca);
        entity.setPesagem(pesagem);

        registroRepository.save(entity);

        return registroMapper.toDTO(entity);
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
