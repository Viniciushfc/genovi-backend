package br.com.genovi.domain.models;

import br.com.genovi.domain.enums.EnumGrauPureza;
import br.com.genovi.domain.enums.EnumRaca;
import br.com.genovi.domain.enums.EnumSexo;
import br.com.genovi.domain.enums.EnumStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ovino")
public class Ovino {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_ovino")
    @SequenceGenerator(name = "gen_ovino", sequenceName = "gen_id_ovino", allocationSize = 1)
    private Long id;

    @Column(unique = true, name = "RFID")
    private Long rfid;

    @Column(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "raca")
    private EnumRaca raca;

    @Column(name = "fbb")
    private String fbb;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "data_nascimento", nullable = true)
    private LocalDateTime dataNascimento;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "data_cadastro", nullable = true)
    private LocalDateTime dataCadastro;

    @Enumerated(EnumType.STRING)
    @Column(name = "grau_pureza")
    private EnumGrauPureza enumGrauPureza;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private EnumSexo sexo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ovino_mae", nullable = true)
    private Ovino ovinoMae;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ovino_pai", nullable = true)
    private Ovino ovinoPai;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EnumStatus status;

    @Column(name = "foto_ovino", nullable = true)
    private String fotoOvino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compra", nullable = true)
    private Compra compra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_parto", nullable = true)
    private Parto parto;

    @OneToMany(mappedBy = "ovino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pesagem> pesagens = new ArrayList<>();

    @Override
    public String toString() {
        return "id: " + id +
                ", rfid: " + rfid +
                ", nome: " + nome +
                ", raca: " + raca +
                ", fbb: " + fbb +
                ", dataNascimento: " + dataNascimento +
                ", dataCadastro: " + dataCadastro +
                ", enumGrauPureza: " + enumGrauPureza +
                ", sexo: " + sexo +
                ", ovinoMae: " + ovinoMae +
                ", ovinoPai: " + ovinoPai +
                ", status: " + status +
                ", fotoOvino: " + fotoOvino +
                ", compra: " + compra +
                ", parto: " + parto +
                ", pesagens: " + pesagens;
    }
}
