/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.repository;

import br.acaitech.springMVC.FluxoDeVenda.entidades.Carrinho;
import br.acaitech.springMVC.FluxoDeVenda.entidades.ItemVenda;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Coutinho's
 */
@Repository
public class CarrinhoRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Carrinho> find() {
        Query jpqlQuery
                = entityManager.createQuery("SELECT c FROM Carrinho c");
        return jpqlQuery.getResultList();
    }
    
    public Carrinho findById(Long id) {
        Query jpqlQuery
                = entityManager.createQuery(
                        "SELECT c FROM Carrinho c WHERE c.id = :idCarrinho")
                        .setParameter("idCarrinho", id);
        Carrinho c = (Carrinho) jpqlQuery.getSingleResult();
        return c;
    }
    
    @Transactional
    public void save(Carrinho c) {
        if (c.getId() == null) {
            entityManager.persist(c);
        } else {
            entityManager.merge(c);
        }
    }

    @Transactional
    public void delete(Long id) {
        Carrinho c = entityManager.find(Carrinho.class, id);
        entityManager.remove(c);
    }
    
}
