package br.com.genovi.application.domain.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Aplicacao")
public class Aplicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDateTime dataAplicacao;
    @ManyToOne
    private Ovino ovino;
    @ManyToOne
    private Medicamento medicamento;
    private boolean temProximaDose;
    private LocalDateTime dataProximaDose;
    @ManyToOne
    private Usuario responsavel;
    private String observacoes;

    public Aplicacao() {
    }

    public Aplicacao(Long id, LocalDateTime dataAplicacao, Ovino ovino, Medicamento medicamento, boolean temProximaDose, LocalDateTime dataProximaDose, Usuario responsavel, String observacoes) {
        this.id = id;
        this.dataAplicacao = dataAplicacao;
        this.ovino = ovino;
        this.medicamento = medicamento;
        this.temProximaDose = temProximaDose;
        this.dataProximaDose = dataProximaDose;
        this.responsavel = responsavel;
        this.observacoes = observacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDateTime dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public Ovino getOvino() {
        return ovino;
    }

    public void setOvino(Ovino ovino) {
        this.ovino = ovino;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public boolean isTemProximaDose() {
        return temProximaDose;
    }

    public void setTemProximaDose(boolean temProximaDose) {
        this.temProximaDose = temProximaDose;
    }

    public LocalDateTime getDataProximaDose() {
        return dataProximaDose;
    }

    public void setDataProximaDose(LocalDateTime dataProximaDose) {
        this.dataProximaDose = dataProximaDose;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
