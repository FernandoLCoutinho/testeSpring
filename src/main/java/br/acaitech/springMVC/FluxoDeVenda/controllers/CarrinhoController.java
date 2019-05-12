/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.controllers;

import br.acaitech.springMVC.FluxoDeVenda.entidades.Carrinho;
import br.acaitech.springMVC.FluxoDeVenda.entidades.ItemVenda;
import br.acaitech.springMVC.FluxoDeVenda.entidades.Produto;
import br.acaitech.springMVC.FluxoDeVenda.repository.CarrinhoRepository;
import br.acaitech.springMVC.FluxoDeVenda.repository.ItemVendaRepository;
import br.acaitech.springMVC.FluxoDeVenda.repository.ProdutoRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Coutinho's
 */
@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemVendaRepository itemRepository;

    @GetMapping
    public ModelAndView listar() {
        List<Carrinho> carrinho;
        carrinho = carrinhoRepository.findAll();
        return new ModelAndView("carrinho/lista").addObject("carrinho", carrinho);
    }

    @GetMapping("/adicionar/{id}")
    public ModelAndView adicionar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        ItemVenda item = new ItemVenda();
        List<ItemVenda> lista;
        lista = itemRepository.findAll();
        if (lista.isEmpty()) {
            Produto p = produtoRepository.findById(id);
            item = new ItemVenda(p, 1);
            itemRepository.save(item);
        } else {
            for (ItemVenda itemVenda : lista) {
                if (itemVenda.getProduto().getId().equals(produtoRepository.findById(id).getId())) {
                    itemVenda.setQuantidade(itemVenda.getQuantidade() + 1);
                    item = itemVenda;
                    itemRepository.save(item);
                }
            }
        }
        if (item != null) {
            List<ItemVenda> listaCarrinho;
            listaCarrinho = itemRepository.findAll();
            if (!listaCarrinho.isEmpty()) {
                Set<ItemVenda> itens = new HashSet<>();
                for (ItemVenda itemVenda : listaCarrinho) {
                    itens.add(itemVenda);
                }
                Carrinho carrinho = new Carrinho(itens);
                carrinhoRepository.save(carrinho);
            } else {
                Set<ItemVenda> itens = new HashSet<>();
                itens.add(item);
                Carrinho carrinho = new Carrinho(itens);
                carrinhoRepository.save(carrinho);
            }
            redirectAttributes.addFlashAttribute("mensagemSucesso", item.getProduto().getNome() + "" + " adicionado ao carrinho com sucesso.");
            return new ModelAndView("redirect:/carrinho");
        } else {
            redirectAttributes.addFlashAttribute("mensagemFalha", "Erro. Produto não pode ser adicionado ao carrinho.");
            return new ModelAndView("redirect:/carrinho");
        }
    }

    @GetMapping("/construir")
    public ModelAndView construirCarrinho(ItemVenda item, RedirectAttributes redirectAttributes) {
        if (item != null) {
            List<ItemVenda> listaCarrinho;
            listaCarrinho = itemRepository.findAll();
            if (!listaCarrinho.isEmpty()) {
                Set<ItemVenda> itens = new HashSet<>();
                for (ItemVenda itemVenda : listaCarrinho) {
                    itens.add(itemVenda);
                }
                Carrinho carrinho = new Carrinho(itens);
                carrinhoRepository.save(carrinho);
            } else {
                Set<ItemVenda> itens = new HashSet<>();
                itens.add(item);
                Carrinho carrinho = new Carrinho(itens);
                carrinhoRepository.save(carrinho);
            }
            redirectAttributes.addFlashAttribute("mensagemSucesso", item.getProduto().getNome() + "" + " adicionado ao carrinho com sucesso.");
            return new ModelAndView("redirect:/carrinho");
        } else {
            redirectAttributes.addFlashAttribute("mensagemFalha", "Erro. Produto não pode ser adicionado ao carrinho.");
            return new ModelAndView("redirect:/carrinho");
        }
    }

    @GetMapping("/vender")
    public ModelAndView vender() {
        return new ModelAndView("redirect:/venda");
    }

}
