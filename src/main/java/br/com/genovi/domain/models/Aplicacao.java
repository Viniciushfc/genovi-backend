package br.com.genovi.domain.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "aplicacao")
public class Aplicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ovino")
    private Ovino ovino;

    @Column(name = "data_aplicacao")
    private LocalDateTime dataAplicacao;

    @ManyToOne
    @JoinColumn(name = "id_medicamento")
    private Medicamento medicamento;

    @Column(name = "data_proxima_dose")
    private LocalDateTime dataProximaDose;

    public Aplicacao() {
    }

    public Aplicacao(Long id, Ovino ovino, LocalDateTime dataAplicacao, Medicamento medicamento, LocalDateTime dataProximaDose) {
        this.id = id;
        this.ovino = ovino;
        this.dataAplicacao = dataAplicacao;
        this.medicamento = medicamento;
        this.dataProximaDose = dataProximaDose;
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

    public LocalDateTime getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDateTime dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public LocalDateTime getDataProximaDose() {
        return dataProximaDose;
    }

    public void setDataProximaDose(LocalDateTime dataProximaDose) {
        this.dataProximaDose = dataProximaDose;
    }
}
