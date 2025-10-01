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
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "gen_funcionario",
            sequenceName = "seq_funcionario",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(unique = true, name = "cpf_cnpj")
    private String cpfCnpj;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "data_admissao")
    private LocalDateTime dataAdmissao;

    @Column(name = "data_recisao")
    private LocalDateTime dataRecisao;
}
