package br.com.genovi.domain.models;

import jakarta.persistence.*;

@Entity
@Table(name = "parto")
public class Parto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ovino_mae")
    private Ovino ovinoMae;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ovino_pai")
    private Ovino ovinoPai;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_gestacao")
    private Gestacao gestacao;

    public Parto() {
    }

    public Parto(Long id, Gestacao gestacao, Ovino ovinoMae, Ovino ovinoPai) {
        this.id = id;
        this.gestacao = gestacao;
        this.ovinoMae = ovinoMae;
        this.ovinoPai = ovinoPai;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gestacao getGestacao() {
        return gestacao;
    }

    public void setGestacao(Gestacao gestacao) {
        this.gestacao = gestacao;
    }

    public Ovino getOvinoMae() {
        return ovinoMae;
    }

    public void setOvinoMae(Ovino ovinoMae) {
        this.ovinoMae = ovinoMae;
    }

    public Ovino getOvinoPai() {
        return ovinoPai;
    }

    public void setOvinoPai(Ovino ovinoPai) {
        this.ovinoPai = ovinoPai;
    }
}
