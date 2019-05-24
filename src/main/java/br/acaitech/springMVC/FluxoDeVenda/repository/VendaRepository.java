/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.repository;

import br.acaitech.springMVC.FluxoDeVenda.entidades.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Coutinho's
 */
@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer>{
}
