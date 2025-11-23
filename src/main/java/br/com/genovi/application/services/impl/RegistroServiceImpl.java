package br.com.genovi.application.services.impl;

import br.com.genovi.application.mapper.PesagemMapper;
import br.com.genovi.application.services.RegistroService;
import br.com.genovi.domain.models.*;
import br.com.genovi.dtos.registro.CreateRegistroDTO;
import br.com.genovi.dtos.registro.RegistroDTO;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.application.mapper.RegistroMapper;
import br.com.genovi.infrastructure.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    @Transactional
    public void createReproducaoRegistro(Reproducao reproducao, Long idFuncionario) {
        Funcionario funcionario = findFuncionarioById(idFuncionario);

        Registro entity = new Registro();
        entity.setDataRegistro(LocalDateTime.now());
        entity.setFuncionario(funcionario);
        entity.setReproducao(reproducao);

        String descricaoRegistro = String.format(
                "Reprodução registrada. Carneiro: %s (RFID: %s, ID: %d), Ovelha: %s (RFID: %s, ID: %d). Tipo: %s. Data: %s.",
                reproducao.getCarneiroPai().getNome(),
                reproducao.getCarneiroPai().getRfid(),
                reproducao.getCarneiroPai().getId(),
                reproducao.getOvelhaMae().getNome(),
                reproducao.getOvelhaMae().getRfid(),
                reproducao.getOvelhaMae().getId(),
                reproducao.getEnumReproducao().getValue(),
                reproducao.getDataReproducao().toString()
        );
        entity.setDescricaoRegistro(descricaoRegistro);

        registroRepository.save(entity);
    }

    @Override
    @Transactional
    public void createGestacaoRegistro(Gestacao gestacao, Long idFuncionario) {
        Funcionario funcionario = findFuncionarioById(idFuncionario);

        Registro entity = new Registro();
        entity.setDataRegistro(LocalDateTime.now());
        entity.setFuncionario(funcionario);
        entity.setGestacao(gestacao);

        String descricaoRegistro = String.format(
                "Gestação registrada para a ovelha %s (RFID: %s, ID: %d). Pai: %s (RFID: %s, ID: %d). Data: %s.",
                gestacao.getOvelhaMae().getNome(),
                gestacao.getOvelhaMae().getRfid(),
                gestacao.getOvelhaMae().getId(),
                gestacao.getOvelhaPai().getNome(),
                gestacao.getOvelhaPai().getRfid(),
                gestacao.getOvelhaPai().getId(),
                gestacao.getDataGestacao().toString()
        );
        entity.setDescricaoRegistro(descricaoRegistro);

        registroRepository.save(entity);
    }

    @Override
    @Transactional
    public void createPartoRegistro(Parto parto, Long idFuncionario) {
        Funcionario funcionario = findFuncionarioById(idFuncionario);

        Registro entity = new Registro();
        entity.setDataRegistro(LocalDateTime.now());
        entity.setFuncionario(funcionario);
        entity.setParto(parto);

        String descricaoRegistro = String.format(
                "Parto registrado para a ovelha %s (RFID: %s, ID: %d). Pai: %s (RFID: %s, ID: %d). Data do parto: %s.",
                parto.getOvinoMae().getNome(),
                parto.getOvinoMae().getRfid(),
                parto.getOvinoMae().getId(),
                parto.getOvinoPai().getNome(),
                parto.getOvinoPai().getRfid(),
                parto.getOvinoPai().getId(),
                parto.getDataParto().toString()
        );
        entity.setDescricaoRegistro(descricaoRegistro);

        registroRepository.save(entity);
    }

    @Override
    @Transactional
    public void createAplicacaoRegistro(Aplicacao aplicacao, Long idFuncionario) {
        Funcionario funcionario = findFuncionarioById(idFuncionario);

        Registro entity = new Registro();
        entity.setDataRegistro(LocalDateTime.now());
        entity.setFuncionario(funcionario);
        entity.setAplicacao(aplicacao);

        String descricaoRegistro = String.format(
                "Aplicação de medicamento no ovino %s (RFID: %s, ID: %d). Medicamento: %s. Data: %s. Próxima dose: %s.",
                aplicacao.getOvino().getNome(),
                aplicacao.getOvino().getRfid(),
                aplicacao.getOvino().getId(),
                aplicacao.getMedicamento().getNome(),
                aplicacao.getDataAplicacao().toString(),
                aplicacao.getDataProximaDose() != null ? aplicacao.getDataProximaDose().toString() : "N/A"
        );
        entity.setDescricaoRegistro(descricaoRegistro);

        registroRepository.save(entity);
    }

    @Override
    @Transactional
    public void createOcorrenciaDoencaRegistro(OcorrenciaDoenca ocorrenciaDoenca, Long idFuncionario) {
        Funcionario funcionario = findFuncionarioById(idFuncionario);

        Registro entity = new Registro();
        entity.setDataRegistro(LocalDateTime.now());
        entity.setFuncionario(funcionario);
        entity.setOcorrenciaDoenca(ocorrenciaDoenca);

        String descricaoRegistro = String.format(
                "Ocorrência de doença registrada para o ovino %s (RFID: %s, ID: %d). Doença: %s. Início: %s. Curada: %s.",
                ocorrenciaDoenca.getOvino().getNome(),
                ocorrenciaDoenca.getOvino().getRfid(),
                ocorrenciaDoenca.getOvino().getId(),
                ocorrenciaDoenca.getDoenca().getNome(),
                ocorrenciaDoenca.getDataInicio().toString(),
                ocorrenciaDoenca.getCurada() != null && ocorrenciaDoenca.getCurada() ? "Sim" : "Não"
        );
        entity.setDescricaoRegistro(descricaoRegistro);

        registroRepository.save(entity);
    }

    @Override
    @Transactional
    public void createPesagemRegistro(Pesagem pesagem, Long idFuncionario) {
        Funcionario funcionario = findFuncionarioById(idFuncionario);

        Registro entity = new Registro();
        entity.setDataRegistro(LocalDateTime.now());
        entity.setFuncionario(funcionario);
        entity.setPesagem(pesagem);

        String descricaoRegistro = String.format(
                "Pesagem registrada para o ovino %s (RFID: %s, ID: %d). Peso: %.2f kg. Data: %s.",
                pesagem.getOvino().getNome(),
                pesagem.getOvino().getRfid(),
                pesagem.getOvino().getId(),
                pesagem.getPeso(),
                pesagem.getDataPesagem().toString()
        );
        entity.setDescricaoRegistro(descricaoRegistro);

        registroRepository.save(entity);
    }
}
