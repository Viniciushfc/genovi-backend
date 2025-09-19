package br.com.genovi.domain.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocorrencia_Doenca")
public class OcorrenciaDoenca {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ovino")
    private Ovino ovino;

    @ManyToOne
    @JoinColumn(name = "id_doenca")
    private Doenca doenca;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_final")
    private LocalDateTime dataFinal;

    @Column(name = "curada")
    private boolean curada;

    @ManyToOne
    @JoinColumn(name = "id_responsavel")
    private Funcionario responsavel;

    public OcorrenciaDoenca() {
    }

    public OcorrenciaDoenca(Long id, Ovino ovino, Doenca doenca, LocalDateTime dataInicio, LocalDateTime dataFim, boolean curada, Funcionario responsavel) {
        this.id = id;
        this.ovino = ovino;
        this.doenca = doenca;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFim;
        this.curada = curada;
        this.responsavel = responsavel;
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

    public Doenca getDoenca() {
        return doenca;
    }

    public void setDoenca(Doenca doenca) {
        this.doenca = doenca;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }

    public boolean isCurada() {
        return curada;
    }

    public void setCurada(boolean curada) {
        this.curada = curada;
    }

    public Funcionario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Funcionario responsavel) {
        this.responsavel = responsavel;
    }
}
