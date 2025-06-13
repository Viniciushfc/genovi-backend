package br.com.genovi.application.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Registro")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    private Ovino ovino;
    @OneToMany
    private List<Parto> partos;
    @OneToMany
    private List<CicloCio> cicloCios;
    @OneToMany
    private List<Amamentacao> amamentacaos;
    @OneToMany
    private List<Reproducao> reproducaos;
    @OneToMany
    private List<Aplicacao> aplicacaos;
    @OneToMany
    private List<OcorrenciaDoenca> ocorrenciaDoencas;

    public Registro() {
    }

    public Registro(Long id, Ovino ovino, List<Parto> partos, List<CicloCio> cicloCios, List<Amamentacao> amamentacaos, List<Reproducao> reproducaos, List<Aplicacao> aplicacaos, List<OcorrenciaDoenca> ocorrenciaDoencas) {
        this.id = id;
        this.ovino = ovino;
        this.partos = partos;
        this.cicloCios = cicloCios;
        this.amamentacaos = amamentacaos;
        this.reproducaos = reproducaos;
        this.aplicacaos = aplicacaos;
        this.ocorrenciaDoencas = ocorrenciaDoencas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ovino getOvino() {
        return ovino;
    }

    public void setOvino(Ovino ovino) {
        this.ovino = ovino;
    }

    public List<Parto> getPartos() {
        return partos;
    }

    public void setPartos(List<Parto> partos) {
        this.partos = partos;
    }

    public List<CicloCio> getCicloCios() {
        return cicloCios;
    }

    public void setCicloCios(List<CicloCio> cicloCios) {
        this.cicloCios = cicloCios;
    }

    public List<Amamentacao> getAmamentacaos() {
        return amamentacaos;
    }

    public void setAmamentacaos(List<Amamentacao> amamentacaos) {
        this.amamentacaos = amamentacaos;
    }

    public List<Reproducao> getReproducaos() {
        return reproducaos;
    }

    public void setReproducaos(List<Reproducao> reproducaos) {
        this.reproducaos = reproducaos;
    }

    public List<Aplicacao> getAplicacaos() {
        return aplicacaos;
    }

    public void setAplicacaos(List<Aplicacao> aplicacaos) {
        this.aplicacaos = aplicacaos;
    }

    public List<OcorrenciaDoenca> getOcorrenciaDoencas() {
        return ocorrenciaDoencas;
    }

    public void setOcorrenciaDoencas(List<OcorrenciaDoenca> ocorrenciaDoencas) {
        this.ocorrenciaDoencas = ocorrenciaDoencas;
    }
}
