package br.com.genovi.application.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Ocorrencia_Doenca")
public class OcorrenciaDoenca {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Ovino ovino;
    private Doenca doenca;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private boolean curada;
    private Usuario responsavel;

    public OcorrenciaDoenca() {
    }

    public OcorrenciaDoenca(Long id, Ovino ovino, Doenca doenca, LocalDateTime dataInicio, LocalDateTime dataFim, boolean curada, Usuario responsavel) {
        this.id = id;
        this.ovino = ovino;
        this.doenca = doenca;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
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

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isCurada() {
        return curada;
    }

    public void setCurada(boolean curada) {
        this.curada = curada;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }
}
