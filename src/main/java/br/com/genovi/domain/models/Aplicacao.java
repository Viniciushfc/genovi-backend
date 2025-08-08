package br.com.genovi.domain.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "aplicacao")
public class Aplicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "data_aplicacao")
    private LocalDateTime dataAplicacao;

    @ManyToOne
    @JoinColumn(name = "id_ovino")
    private Ovino ovino;

    @ManyToOne
    @JoinColumn(name = "id_medicamento")
    private Medicamento medicamento;

    @Column(name = "tem_proxima_dose")
    private boolean temProximaDose;

    @Column(name = "data_proxima_dose")
    private LocalDateTime dataProximaDose;

    @ManyToOne
    @JoinColumn(name = "id_responsavel")
    private Usuario responsavel;

    @Column(name = "observacoes")
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
