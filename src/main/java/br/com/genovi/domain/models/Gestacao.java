package br.com.genovi.domain.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "gestacao")
public class Gestacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ovelha_mae")
    private Ovino ovelhaMae;

    @ManyToOne
    @JoinColumn(name = "id_ovelha_PAI")
    private Ovino ovelhaPai;

    @OneToOne
    @JoinColumn(name = "id_reproducao")
    private Reproducao reproducao;

    public Gestacao() {
    }

    public Gestacao(Long id, Ovino ovelhaMae, Ovino ovelhaPai, Reproducao reproducao) {
        this.id = id;
        this.ovelhaMae = ovelhaMae;
        this.ovelhaPai = ovelhaPai;
        this.reproducao = reproducao;
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

    public Ovino getOvelhaPai() {
        return ovelhaPai;
    }

    public void setOvelhaPai(Ovino ovelhaPai) {
        this.ovelhaPai = ovelhaPai;
    }

    public Reproducao getReproducao() {
        return reproducao;
    }

    public void setReproducao(Reproducao reproducao) {
        this.reproducao = reproducao;
    }
}
