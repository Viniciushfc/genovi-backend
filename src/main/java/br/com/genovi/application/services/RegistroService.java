package br.com.genovi.application.services;

import br.com.genovi.domain.models.*;
import br.com.genovi.dtos.registro.RegistroDTO;
import br.com.genovi.infrastructure.mappers.RegistroMapper;
import br.com.genovi.infrastructure.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroService {

    private final OvinoRepository ovinoRepository;
    private final PartoRepository partoRepository;
    private final CicloCioRepository cicloCioRepository;
    private final AmamentacaoRepository amamentacaoRepository;
    private final ReproducaoRepository reproducaoRepository;
    private final AplicacaoRepository aplicacaoRepository;
    private final OcorrenciaDoencaRepository ocorrenciaDoencaRepository;
    private final RegistroMapper registroMapper;


    public RegistroService(
            OvinoRepository ovinoRepository,
            PartoRepository partoRepository,
            CicloCioRepository cicloCioRepository,
            AmamentacaoRepository amamentacaoRepository,
            ReproducaoRepository reproducaoRepository,
            AplicacaoRepository aplicacaoRepository,
            OcorrenciaDoencaRepository ocorrenciaDoencaRepository,
            RegistroMapper registroMapper) {
        this.ovinoRepository = ovinoRepository;
        this.partoRepository = partoRepository;
        this.cicloCioRepository = cicloCioRepository;
        this.amamentacaoRepository = amamentacaoRepository;
        this.reproducaoRepository = reproducaoRepository;
        this.aplicacaoRepository = aplicacaoRepository;
        this.ocorrenciaDoencaRepository = ocorrenciaDoencaRepository;
        this.registroMapper = registroMapper;
    }

    public RegistroDTO gerarRegistroPorOvinoById(Long ovinoId) {
        Ovino ovino = ovinoRepository.findById(ovinoId)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrado"));

        List<Parto> partos = partoRepository.findByOvelhaMaeId(ovinoId);
        List<CicloCio> cicloCios = cicloCioRepository.findByOvelhaId(ovinoId);
        List<Amamentacao> amamentacoes = amamentacaoRepository.findByOvelhaMaeIdOrCordeiroMamandoId(ovinoId, ovinoId);
        List<Reproducao> reproducoes = reproducaoRepository.findByCarneiroPaiIdOrOvelhaMaeId(ovinoId, ovinoId);
        List<Aplicacao> aplicacoes = aplicacaoRepository.findByOvinoId(ovinoId);
        List<OcorrenciaDoenca> ocorrencias = ocorrenciaDoencaRepository.findByOvinoId(ovinoId);

        RegistroRecord registro = registroMapper.toEntity(ovino, partos, cicloCios, amamentacoes, reproducoes, aplicacoes, ocorrencias);
        return registroMapper.toDTO(registro);
    }
}

