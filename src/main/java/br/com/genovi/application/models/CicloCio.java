package br.com.genovi.application.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Ciclo_Cio")
public class CicloCio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Ovino Ovelha;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String observacoes;

    public CicloCio() {
    }

    public CicloCio(Long id, Ovino ovelha, LocalDateTime dataInicio, LocalDateTime dataFim, String observacoes) {
        this.id = id;
        Ovelha = ovelha;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.observacoes = observacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ovino getOvelha() {
        return Ovelha;
    }

    public void setOvelha(Ovino ovelha) {
        Ovelha = ovelha;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
