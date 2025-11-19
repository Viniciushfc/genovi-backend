package br.com.genovi.application.services.impl;

import br.com.genovi.application.services.PesagemService;
import br.com.genovi.domain.models.Ovino;
import br.com.genovi.domain.models.Pesagem;
import br.com.genovi.infrastructure.exception.exceptionCustom.ResourceNotFoundException;
import br.com.genovi.dtos.pesagem.CreatePesagemDTO;
import br.com.genovi.dtos.pesagem.PesagemDTO;
import br.com.genovi.application.mapper.PesagemMapper;
import br.com.genovi.infrastructure.repository.OvinoRepository;
import br.com.genovi.infrastructure.repository.PesagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PesagemServiceImpl implements PesagemService {

    private final PesagemRepository pesagemRepository;
    private final OvinoRepository ovinoRepository;
    private final PesagemMapper pesagemMapper;

    private Pesagem findPesagemById(Long id) {
        if (id == null) return null;
        return pesagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pesagem não encontrada"));
    }

    private Ovino findOvinoById(Long id) {
        if (id == null) return null;
        return ovinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ovino não encontrado para Pesagem"));
    }

    @Override
    public List<PesagemDTO> findAll() {
        return pesagemRepository.findAll().stream().map(pesagemMapper::toDTO).toList();
    }

    @Override
    public PesagemDTO findById(Long id) {
        return pesagemMapper.toDTO(findPesagemById(id));
    }

    @Override
    public PesagemDTO save(CreatePesagemDTO dto) {
        Ovino ovino = findOvinoById(dto.idOvino());

        Pesagem entity = new Pesagem();
        entity.setOvino(ovino);
        entity.setDataPesagem(dto.dataPesagem());
        entity.setPeso(dto.pesagem());

        pesagemRepository.save(entity);

        return pesagemMapper.toDTO(entity);
    }

    @Override
    public PesagemDTO update(Long id, CreatePesagemDTO dto) {
        Pesagem entity = findPesagemById(id);
        Ovino ovino = findOvinoById(dto.idOvino());

        entity.setOvino(ovino);
        entity.setDataPesagem(dto.dataPesagem());
        entity.setPeso(dto.pesagem());

        pesagemRepository.save(entity);

        return pesagemMapper.toDTO(entity);
    }

    @Override
    public void delete(Long id) {
        findPesagemById(id);
        pesagemRepository.deleteById(id);
    }
}
