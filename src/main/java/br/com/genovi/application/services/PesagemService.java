package br.com.genovi.application.services;

import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Pesagem;
import br.com.genovi.dtos.pesagem.CreatePesagemDTO;
import br.com.genovi.dtos.pesagem.PesagemDTO;
import br.com.genovi.infrastructure.mappers.PesagemMapper;
import br.com.genovi.infrastructure.repositories.OvinoRepository;
import br.com.genovi.infrastructure.repositories.PesagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PesagemService {

    private final PesagemRepository pesagemRepository;
    private final OvinoRepository ovinoRepository;
    private final PesagemMapper pesagemMapper;

    public PesagemService(PesagemRepository pesagemRepository, OvinoRepository ovinoRepository, PesagemMapper pesagemMapper) {
        this.pesagemRepository = pesagemRepository;
        this.ovinoRepository = ovinoRepository;
        this.pesagemMapper = pesagemMapper;
    }

    private Pesagem findPesagemById(Long id) {
        return pesagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pesagem não encontrada"));
    }

    private Ovino findOvinoById(Long id) {
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ovino não encontrado para Pesagem"));
    }

    public List<PesagemDTO> findAll() {
        return pesagemRepository.findAll().stream().map(pesagemMapper::toDTO).toList();
    }

    public PesagemDTO findById(Long id) {
        return pesagemMapper.toDTO(findPesagemById(id));
    }

    public PesagemDTO save(CreatePesagemDTO dto) {
        Ovino ovino = findOvinoById(dto.idOvino());

        Pesagem pesagem = pesagemMapper.toEntity(dto, ovino);

        return pesagemMapper.toDTO(pesagemRepository.save(pesagem));
    }

    public PesagemDTO update(Long id, CreatePesagemDTO dto) {
        Pesagem entity = findPesagemById(id);
        Ovino ovino = findOvinoById(dto.idOvino());

        pesagemMapper.updateEntityFromDTO(dto, entity, ovino);

        return pesagemMapper.toDTO(pesagemRepository.save(entity));
    }

    public void delete(Long id) {
        findPesagemById(id);
        pesagemRepository.deleteById(id);
    }
}
