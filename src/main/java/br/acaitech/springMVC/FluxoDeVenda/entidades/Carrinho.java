/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.entidades;

import java.io.Serializable;
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
public class Carrinho implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = true, updatable = false, nullable = false, unique = true)
    private Long id;
    
    @ManyToOne
    private ItemVenda itens;

    @ManyToOne
    private Produto produto;

    public Carrinho() {
    }

    public Carrinho(Long id, ItemVenda itens, Produto produto) {
        this.id = id;
        this.itens = itens;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemVenda getItens() {
        return itens;
    }

    public void setItens(ItemVenda itens) {
        this.itens = itens;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    
    
}
