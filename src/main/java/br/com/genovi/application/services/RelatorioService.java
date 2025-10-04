package br.com.genovi.application.services;

public interface RelatorioService {
    byte[] gerarRelatorioRegistro(Long id) throws Exception;
}