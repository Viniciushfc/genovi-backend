package br.com.genovi.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registro")
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "gen_registro",
            sequenceName = "seq_registro",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;

    @Column(name = "is_sugestao")
    private Boolean isSugestao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reproducao", nullable = true)
    private Reproducao reproducao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_gestacao", nullable = true)
    private Gestacao gestacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_parto", nullable = true)
    private Parto parto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_aplicacao", nullable = true)
    private Aplicacao aplicacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ocorrencia_doenca", nullable = true)
    private OcorrenciaDoenca ocorrenciaDoenca;
}
