package br.com.genovi.domain.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Amamentacao")
public class Amamentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Ovino ovelhaMae;
    @ManyToOne
    private Ovino cordeiroMamando;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String observacoes;

    public Amamentacao() {
    }

    public Amamentacao(Long id, Ovino ovelhaMae, Ovino cordeiroMamando, LocalDateTime dataInicio, LocalDateTime dataFim, String observacoes) {
        this.id = id;
        this.ovelhaMae = ovelhaMae;
        this.cordeiroMamando = cordeiroMamando;
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

    public Ovino getOvelhaMae() {
        return ovelhaMae;
    }

    public void setOvelhaMae(Ovino ovelhaMae) {
        this.ovelhaMae = ovelhaMae;
    }

    public Ovino getCordeiroMamando() {
        return cordeiroMamando;
    }

    public void setCordeiroMamando(Ovino cordeiroMamando) {
        this.cordeiroMamando = cordeiroMamando;
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
