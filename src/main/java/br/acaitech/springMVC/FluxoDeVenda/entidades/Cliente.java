/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.entidades;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author Coutinho's
 */
@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDateTime dtNascimento;
    
    @Column(nullable = false)
    private String estado;
    
    @Column(nullable = false)
    private String cidade;
    
    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String rua;
    
    @Column(nullable = false)
    private Integer numeroCasa;
    
    @Column(nullable = false, length = 8)
    private Integer cep;

    @Column(nullable = false, insertable = true, updatable = false)
    private LocalDateTime dtCadastro;

    @Transient
    private String observacoes;

    public Cliente() {
    }

    public Cliente(Long id, String nome, LocalDateTime dtNascimento, String estado, String cidade, String bairro, String rua, Integer numeroCasa, Integer cep, LocalDateTime dtCadastro, String observacoes) {
        this.id = id;
        this.nome = nome;
        this.dtNascimento = dtNascimento;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numeroCasa = numeroCasa;
        this.cep = cep;
        this.dtCadastro = dtCadastro;
        this.observacoes = observacoes;
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

    public LocalDateTime getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDateTime dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(Integer numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public LocalDateTime getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDateTime dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    

}
