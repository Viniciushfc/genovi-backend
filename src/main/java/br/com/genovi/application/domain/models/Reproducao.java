package br.com.genovi.application.domain.models;

import br.com.genovi.application.domain.enums.TypeReproducao;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reproducao")
public class Reproducao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDateTime dataReproducao;
    @ManyToOne
    private Ovino carneiroPai;
    @ManyToOne
    private Ovino ovelhaMae;
    private TypeReproducao typeReproducao;
    private String observacoes;

    public Reproducao() {
    }

    public Reproducao(Long id, LocalDateTime dataReproducao, Ovino carneiroPai, Ovino ovelhaMae, TypeReproducao typeReproducao, String observacoes) {
        this.id = id;
        this.dataReproducao = dataReproducao;
        this.carneiroPai = carneiroPai;
        this.ovelhaMae = ovelhaMae;
        this.typeReproducao = typeReproducao;
        this.observacoes = observacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataReproducao() {
        return dataReproducao;
    }

    public void setDataReproducao(LocalDateTime dataReproducao) {
        this.dataReproducao = dataReproducao;
    }

    public Ovino getCarneiroPai() {
        return carneiroPai;
    }

    public void setCarneiroPai(Ovino carneiroPai) {
        this.carneiroPai = carneiroPai;
    }

    public Ovino getOvelhaMae() {
        return ovelhaMae;
    }

    public void setOvelhaMae(Ovino ovelhaMae) {
        this.ovelhaMae = ovelhaMae;
    }

    public TypeReproducao getTypeReproducao() {
        return typeReproducao;
    }

    public void setTypeReproducao(TypeReproducao typeReproducao) {
        this.typeReproducao = typeReproducao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
