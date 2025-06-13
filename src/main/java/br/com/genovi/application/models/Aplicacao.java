package br.com.genovi.application.models;

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
    private LocalDateTime proximaDose;
    @ManyToOne
    private Usuario responsavel;
    private String observacoes;

    public Aplicacao() {
    }

    public Aplicacao(Long id, LocalDateTime dataAplicacao, Ovino ovino, Vacina vacina, boolean temProximaDose, LocalDateTime proximaDose, Usuario responsavel, String observacoes) {
        this.id = id;
        this.dataAplicacao = dataAplicacao;
        this.ovino = ovino;
        this.vacina = vacina;
        this.temProximaDose = temProximaDose;
        this.proximaDose = proximaDose;
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

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public boolean isTemProximaDose() {
        return temProximaDose;
    }

    public void setTemProximaDose(boolean temProximaDose) {
        this.temProximaDose = temProximaDose;
    }

    public LocalDateTime getProximaDose() {
        return proximaDose;
    }

    public void setProximaDose(LocalDateTime proximaDose) {
        this.proximaDose = proximaDose;
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
