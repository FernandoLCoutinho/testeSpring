/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.controllers;

import br.acaitech.springMVC.FluxoDeVenda.entidades.Carrinho;
import br.acaitech.springMVC.FluxoDeVenda.entidades.CarrinhoVenda;
import br.acaitech.springMVC.FluxoDeVenda.entidades.ItemVenda;
import br.acaitech.springMVC.FluxoDeVenda.repository.CarrinhoRepository;
import br.acaitech.springMVC.FluxoDeVenda.repository.CarrinhoVendaRepository;
import br.acaitech.springMVC.FluxoDeVenda.repository.ItemVendaRepository;
import br.acaitech.springMVC.FluxoDeVenda.repository.ProdutoRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private CarrinhoVendaRepository carrinhoVendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemVendaRepository itemRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @GetMapping
    public ModelAndView listar(Carrinho carrinho) {
        List<Carrinho> carrinhoLista = carrinhoRepository.find();
        return new ModelAndView("carrinho/lista").addObject("carrinho", carrinhoLista);
    }

    @GetMapping("/adicionar/{id}")
    public ModelAndView adicionar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        ItemVenda item = new ItemVenda();
        List<ItemVenda> lista;
        lista = itemRepository.findAll();
        if (!lista.contains(item)) {
            item.setProduto(produtoRepository.findById(id));
            item.setQuantidade(1);
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
            Long idItem = item.getId();
            List<ItemVenda> listaCarrinho;
            listaCarrinho = itemRepository.findAll();
            if (listaCarrinho.isEmpty()) {
                Set<ItemVenda> itens = new HashSet<>();
                itens.add(item);
                CarrinhoVenda carrinho = new CarrinhoVenda(itens);
                carrinhoVendaRepository.save(carrinho);
            } else {
                Set<ItemVenda> itens = new HashSet<>();
                for (ItemVenda itemVenda : listaCarrinho) {
                    itens.add(itemVenda);
                }
                CarrinhoVenda carrinho = new CarrinhoVenda(itens);

                carrinhoVendaRepository.save(carrinho);

            }
            redirectAttributes.addFlashAttribute("mensagemSucesso", item.getProduto().getNome() + "" + " adicionado ao carrinho com sucesso.");
            return new ModelAndView("redirect:/carrinho/construir").addObject("idItem", idItem);
        } else {
            redirectAttributes.addFlashAttribute("mensagemFalha", "Erro. Produto não pode ser adicionado ao carrinho.");
            return new ModelAndView("redirect:/carrinho/construir");
        }
    }

    @PostMapping("/construir")
    public ModelAndView construirCarrinho(Long idItem, RedirectAttributes redirectAttributes) {
        if (idItem != null) {
            Carrinho carrinho = new Carrinho();
            ItemVenda item = itemRepository.findById(idItem);
            List<ItemVenda> listaItens;
            listaItens = itemRepository.findAll();
            List<Carrinho> carrinhoLista = new ArrayList<>();
            carrinhoLista = carrinhoRepository.find();
            for (int i = 0; i < listaItens.size(); i++) {
                carrinho.setItens(listaItens.get(i));
                carrinho.setProduto(listaItens.get(i).getProduto());
            }
            for (Carrinho carrinho1 : carrinhoLista) {
                if(Objects.equals(carrinho1.getItens().getId(), carrinho.getItens().getId())){
                    redirectAttributes.addFlashAttribute("mensagemFalha", "Erro. Produto já se encontra no carrinho. Altere a quantidade");
                    return new ModelAndView("redirect:/carrinho");
                }
            }
                    
            carrinhoRepository.save(carrinho);
            redirectAttributes.addFlashAttribute("mensagemSucesso", item.getProduto().getNome() + " adicionado ao carrinho com sucesso.");
            return new ModelAndView("redirect:/carrinho");
        } else {
            redirectAttributes.addFlashAttribute("mensagemFalha", "Erro. Produto não pode ser adicionado ao carrinho.");
            return new ModelAndView("redirect:/carrinho");
        }
    }
    
    @GetMapping("/editar/{id}")
    public ModelAndView editar(){
        return new ModelAndView("redirect:/carrinho");
    }

    @GetMapping("/vender")
    public ModelAndView vender() {
        return new ModelAndView("redirect:/index");
    }

}
