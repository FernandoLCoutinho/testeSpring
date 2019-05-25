/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Coutinho's
 */
@Entity
public class CarrinhoVenda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<ItemVenda> itens;

    public CarrinhoVenda() {
    }

    public CarrinhoVenda(Set<ItemVenda> itens) {
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(Set<ItemVenda> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return "CarrinhoVenda{" + "id=" + id + "itens=" + itens + '}';
    }

}
