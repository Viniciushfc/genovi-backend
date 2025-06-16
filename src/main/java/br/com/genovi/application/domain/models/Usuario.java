package br.com.genovi.application.domain.models;

import br.com.genovi.application.domain.enums.TypeUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private TypeUsuario perfil;
    //A autenticacao2fa precisa ser "Boolean" devido a possibilidade de ser null
    private Boolean autenticacao2fa;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String email, String senha, TypeUsuario perfil, Boolean autenticacao2fa) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.autenticacao2fa = autenticacao2fa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TypeUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(TypeUsuario perfil) {
        this.perfil = perfil;
    }

    public Boolean getAutenticacao2fa() {
        return autenticacao2fa;
    }

    public void setAutenticacao2fa(Boolean autenticacao2fa) {
        this.autenticacao2fa = autenticacao2fa;
    }
}
