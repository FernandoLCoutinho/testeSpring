/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.repository;

import br.acaitech.springMVC.FluxoDeVenda.entidades.Produto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fernando.tsuda
 */
@Repository
public class ProdutoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Produto> findAll(Integer offset, Integer quantidade) {
        Query jpqlQuery
                = entityManager.createNamedQuery("Produto.findAll")
                        .setFirstResult(offset)
                        .setMaxResults(quantidade);
        return jpqlQuery.getResultList();
    }

    public List<Produto> findByCategoria(List<Integer> idsCat) {
        Query jpqlQuery
                = entityManager.createNamedQuery("Produto.findByCategoria")
                .setParameter("idsCat", idsCat);
        return jpqlQuery.getResultList();
    }

    public Produto findById(Long id) {
        Query jpqlQuery
                = entityManager.createNamedQuery("Produto.findById").setParameter("idProd", id);
        Produto p = (Produto) jpqlQuery.getSingleResult();
        return p;
    }

    @Transactional
    public void save(Produto p) {
        if (p.getId() == null) {
            // Salva um novo produto
            entityManager.persist(p);
        } else {
            // Atualiza um produto existente
            entityManager.merge(p);
        }
    }

    @Transactional
    public void delete(Long id) {
        // TEM QUE FAZER CONSULTA PARA ESTAR ASSOCIADO AO
        // ENTITY MANAGER (ATTACHED)
        Produto p = entityManager.find(Produto.class, id);
        entityManager.remove(p);
    }

}
