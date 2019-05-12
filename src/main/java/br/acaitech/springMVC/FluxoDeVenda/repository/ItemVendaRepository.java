/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.repository;

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
public class ItemVendaRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<ItemVenda> findAll() {
        Query jpqlQuery
                = entityManager.createQuery("SELECT i FROM ItemVenda i");
        return jpqlQuery.getResultList();
    }
    
    public ItemVenda findById(Long id) {
        Query jpqlQuery
                = entityManager.createQuery(
                        "SELECT c FROM ItemVenda I WHERE i.id = :idItem")
                        .setParameter("idItem", id);
        ItemVenda i = (ItemVenda) jpqlQuery.getSingleResult();
        return i;
    }
    
    @Transactional
    public void save(ItemVenda i) {
        if (i.getId() == null) {
            entityManager.persist(i);
        } else {
            entityManager.merge(i);
        }
    }
    
    @Transactional
    public void delete(Long id) {
        ItemVenda i = entityManager.find(ItemVenda.class, id);
        entityManager.remove(i);
    }
    
}
