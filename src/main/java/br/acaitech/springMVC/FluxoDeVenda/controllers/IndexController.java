/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.controllers;

import br.acaitech.springMVC.FluxoDeVenda.entidades.Produto;
import br.acaitech.springMVC.FluxoDeVenda.repository.ProdutoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Coutinho's
 */
@Controller
@RequestMapping("/")
public class IndexController {
    
    public String index(){
        return "";
    }
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @GetMapping
    public ModelAndView listar(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "qtd", defaultValue = "100") int qtd,
            @RequestParam(name = "idsCat", required = false) List<Integer> idsCat) {
        List<Produto> produtos;
        if (idsCat != null && !idsCat.isEmpty()) {
            // Busca filtrando pelos IDs das categorias informados
            produtos = produtoRepository.findByCategoria(idsCat);
        } else {
            // Busca normal
            produtos = produtoRepository.findAll(offset, qtd);
        }
        return new ModelAndView("index").addObject("produtos", produtos);
    }
    
}
