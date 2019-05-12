/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.repository;

import br.acaitech.springMVC.FluxoDeVenda.entidades.Categoria;
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
public class CategoriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Categoria> findAll() {
        Query jpqlQuery
                = entityManager.createQuery("SELECT c FROM Categoria c");
        return jpqlQuery.getResultList();
    }

    public Categoria findById(Integer id) {
        Query jpqlQuery
                = entityManager.createQuery(
                        "SELECT c FROM Categoria c WHERE c.id = :idCat")
                        .setParameter("idCat", id);
        Categoria c = (Categoria) jpqlQuery.getSingleResult();
        return c;
    }

    @Transactional
    public void save(Categoria c) {
        if (c.getId() == null) {
            // Criando uma categoria nova
            entityManager.persist(c);
        } else {
            // Atualiza uma categoria já existente
            entityManager.merge(c);
        }
    }

    @Transactional
    public void delete(Integer id) {
        Categoria c = entityManager.find(Categoria.class, id);
        entityManager.remove(c);
    }

}
