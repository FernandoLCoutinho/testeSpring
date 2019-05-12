/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author fernando.tsuda
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
    @NamedQuery(name = "Produto.findByCategoria", query = "SELECT p FROM Produto p INNER JOIN p.categorias c WHERE c.id IN :idsCat"),
    @NamedQuery(name = "Produto.findById", query = "SELECT p FROM Produto p WHERE p.id = :idProd")
    //@NamedQuery(name = "Produto.findById", query = "SELECT p FROM Produto p LEFT JOIN FETCH p.categorias WHERE p.id = :idProd") // USAR O FETCH QUANDO OPEN IN VIEW = FALSE
})
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 1000)
    private String descricao;

    @Column(precision = 6, scale = 2, nullable = false)
    private BigDecimal precoCompra;

    @Column(precision = 6, scale = 2, nullable = false)
    private BigDecimal precoVenda;

    @Column(precision = 6, nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private boolean disponivel;

    @Column(nullable = false, insertable = true, updatable = false)
    private LocalDateTime dtCadastro;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PRODUTO_CATEGORIA",
            joinColumns = @JoinColumn(name = "ID_PRODUTO"),
            inverseJoinColumns = @JoinColumn(name = "ID_CATEGORIA")
    )
    private Set<Categoria> categorias;

    // "produto" é o atributo na classe ImagemProduto onde o @ManyToOne foi configurado
    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
    private Set<ImagemProduto> imagens;

    @Transient
    private Set<Integer> idsCategorias;

    @Transient
    private String observacoes;

    public Produto() {

    }

    public Produto(Long id, String nome, String descricao, BigDecimal precoCompra, BigDecimal precoVenda, int quantidade, boolean disponivel, LocalDateTime dtCadastro) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.quantidade = quantidade;
        this.disponivel = disponivel;
        this.dtCadastro = dtCadastro;
    }

    public Produto(Long id, String nome, String descricao, BigDecimal precoCompra, BigDecimal precoVenda, int quantidade, boolean disponivel, LocalDateTime dtCadastro, Set<ImagemProduto> imagens, Set<Categoria> categorias) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.quantidade = quantidade;
        this.disponivel = disponivel;
        this.dtCadastro = dtCadastro;
        this.imagens = imagens;
        this.categorias = categorias;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(BigDecimal precoCompra) {
        this.precoCompra = precoCompra;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public LocalDateTime getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDateTime dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Set<ImagemProduto> getImagens() {
        return imagens;
    }

    public void setImagens(Set<ImagemProduto> imagens) {
        this.imagens = imagens;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Set<Integer> getIdsCategorias() {
        return idsCategorias;
    }

    public void setIdsCategorias(Set<Integer> idsCategorias) {
        this.idsCategorias = idsCategorias;
    }

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", precoCompra=" + precoCompra + ", precoVenda=" + precoVenda + ", quantidade=" + quantidade + ", dtCadastro=" + dtCadastro + ", categorias=" + categorias + ", imagens=" + imagens + ", idsCategorias=" + idsCategorias + ", observacoes=" + observacoes + '}';
    }

}
