package br.com.genovi.application.services;

import br.com.genovi.dtos.usuario.CreateUsuarioDTO;
import br.com.genovi.dtos.usuario.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> findAll();
    UsuarioDTO findById(Long id);
    UsuarioDTO save(CreateUsuarioDTO dto);
    UsuarioDTO update(Long id, CreateUsuarioDTO dto);
    void disable(Long id);
}
