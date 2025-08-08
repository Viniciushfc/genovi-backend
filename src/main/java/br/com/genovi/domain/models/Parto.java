package br.com.genovi.domain.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "parto")
public class Parto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ovelha_mae")
    private Ovino ovelhaMae;

    @OneToMany
    @JoinColumn(name = "id_parto")
    private List<Ovino> animaisCriados;

    @Column(name = "data_parto")
    private LocalDateTime dataParto;

    @Column(name = "numero_crias")
    private int numeroCrias;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "rejeicao_materna")
    private boolean rejeicaoMaterna;

    @OneToOne
    @JoinColumn(name = "id_reproducaos")
    private Reproducao reproducaos;

    public Parto() {
    }

    public Parto(Long id, Ovino ovelhaMae, List<Ovino> animaisCriados, LocalDateTime dataParto, int numeroCrias, String observacoes, boolean rejeicaoMaterna, Reproducao reproducaos) {
        this.id = id;
        this.ovelhaMae = ovelhaMae;
        this.animaisCriados = animaisCriados;
        this.dataParto = dataParto;
        this.numeroCrias = numeroCrias;
        this.observacoes = observacoes;
        this.rejeicaoMaterna = rejeicaoMaterna;
        this.reproducaos = reproducaos;
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

    public List<Ovino> getAnimaisCriados() {
        return animaisCriados;
    }

    public void setAnimaisCriados(List<Ovino> animaisCriados) {
        this.animaisCriados = animaisCriados;
    }

    public LocalDateTime getDataParto() {
        return dataParto;
    }

    public void setDataParto(LocalDateTime dataParto) {
        this.dataParto = dataParto;
    }

    public int getNumeroCrias() {
        return numeroCrias;
    }

    public void setNumeroCrias(int numeroCrias) {
        this.numeroCrias = numeroCrias;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public boolean isRejeicaoMaterna() {
        return rejeicaoMaterna;
    }

    public void setRejeicaoMaterna(boolean rejeicaoMaterna) {
        this.rejeicaoMaterna = rejeicaoMaterna;
    }

    public Reproducao getReproducaos() {
        return reproducaos;
    }

    public void setReproducaos(Reproducao reproducaos) {
        this.reproducaos = reproducaos;
    }
}
