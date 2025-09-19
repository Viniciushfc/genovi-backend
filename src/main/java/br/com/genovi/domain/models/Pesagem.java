package br.com.genovi.domain.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pesagem")
public class Pesagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "data_pesagem")
    private LocalDateTime dataPesagem;

    @ManyToOne
    @JoinColumn(name = "ovino_id", nullable = false)
    private Ovino ovino;

    public Pesagem() {
    }

    public Pesagem(Long id, LocalDateTime dataPesagem, Ovino ovino) {
        this.id = id;
        this.dataPesagem = dataPesagem;
        this.ovino = ovino;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataPesagem() {
        return dataPesagem;
    }

    public void setDataPesagem(LocalDateTime dataPesagem) {
        this.dataPesagem = dataPesagem;
    }

    public Ovino getOvino() {
        return ovino;
    }

    public void setOvino(Ovino ovino) {
        this.ovino = ovino;
    }
}
