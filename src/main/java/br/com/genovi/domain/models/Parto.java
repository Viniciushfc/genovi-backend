package br.com.genovi.domain.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "parto")
public class Parto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ovino_mae")
    private Ovino ovinoMae;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ovino_pai")
    private Ovino ovinoPai;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_gestacao", nullable = true)
    private Gestacao gestacao;

    @Column(name = "data_parto")
    private LocalDateTime dataParto;

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
