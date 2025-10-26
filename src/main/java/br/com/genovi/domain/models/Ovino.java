package br.com.genovi.domain.models;

import br.com.genovi.domain.enums.TypeGrauPureza;
import br.com.genovi.domain.enums.TypeRaca;
import br.com.genovi.domain.enums.TypeSexo;
import br.com.genovi.domain.enums.TypeStatus;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "gen_ovino", sequenceName = "seq_ovino", allocationSize = 1)
    private Long id;

    @Column(unique = true, name = "RFID")
    private Long rfid;

    @Column(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "raca")
    private TypeRaca raca;

    @Column(name = "fbb")
    private String fbb;

    @Column(name = "data_nascimento", nullable = true)
    private LocalDateTime dataNascimento;

    @Column(name = "data_cadastro", nullable = true)
    private LocalDateTime dataCadastro;

    @Enumerated(EnumType.STRING)
    @Column(name = "grau_pureza")
    private TypeGrauPureza typeGrauPureza;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private TypeSexo sexo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ovino_mae", nullable = true)
    private Ovino ovinoMae;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ovino_pai", nullable = true)
    private Ovino ovinoPai;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TypeStatus status;

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
}
