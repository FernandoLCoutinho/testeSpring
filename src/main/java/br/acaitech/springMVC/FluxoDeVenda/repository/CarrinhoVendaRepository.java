/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.repository;

import br.acaitech.springMVC.FluxoDeVenda.entidades.CarrinhoVenda;
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
public class CarrinhoVendaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CarrinhoVenda> find() {
        Query jpqlQuery
                = entityManager.createQuery("SELECT c FROM CarrinhoVenda c");
        return jpqlQuery.getResultList();
    }

    @Transactional
    public void save(CarrinhoVenda c) {
        if (c.getId() == null) {
            entityManager.persist(c);
        } else {
            entityManager.merge(c);
        }
    }

    @Transactional
    public void delete(Long id) {
        CarrinhoVenda c = entityManager.find(CarrinhoVenda.class, id);
        entityManager.remove(c);
    }

}
