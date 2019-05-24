/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Coutinho's
 */
@Entity
public class ItemVenda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, precision = 2)
    private BigDecimal preçoItem;

    public ItemVenda() {
    }

    public ItemVenda(Produto produto, Integer quantidade, BigDecimal preçoItem) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.preçoItem = preçoItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreçoItem() {
        return preçoItem;
    }

    public void setPreçoItem(BigDecimal preçoItem) {
        this.preçoItem = preçoItem;
    }

    @Override
    public String toString() {
        return "ItemVenda{" + "id=" + id + ", produto=" + produto + ", quantidade=" + quantidade + ", " + '}';
    }

}
