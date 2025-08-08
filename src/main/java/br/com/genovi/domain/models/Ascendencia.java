package br.com.genovi.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ascendencia")
public class Ascendencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_ovino_pai")
    private Ovino pai;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_ovino_mae")
    private Ovino mae;

    public Ascendencia() {

    }

    public Ascendencia(Long id, Ovino pai, Ovino mae) {
        this.id = id;
        this.pai = pai;
        this.mae = mae;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ovino getPai() {
        return pai;
    }

    public void setPai(Ovino pai) {
        this.pai = pai;
    }

    public Ovino getMae() {
        return mae;
    }

    public void setMae(Ovino mae) {
        this.mae = mae;
    }
}
