/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author fernando.tsuda
 */
@Entity
public class ImagemProduto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nomeArquivo;

    @Column(length = 1000)
    private String legenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto")
    private Produto produto;

    public ImagemProduto() {

    }

    public ImagemProduto(String nomeArquivo, String legenda) {
        this.nomeArquivo = nomeArquivo;
        this.legenda = legenda;
    }

    public ImagemProduto(Long id, String nomeArquivo, String legenda) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.legenda = legenda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getUrlArquivo() {
        // MOCK
        return "http://lorempixel.com/400/200/sports/";
    }

    @Override
    public String toString() {
        return "ImagemProduto{" + "id=" + id + ", legenda=" + legenda + ", nomeArquivo=" + nomeArquivo + '}';
    }

}
