/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.controllers;

import br.acaitech.springMVC.FluxoDeVenda.entidades.Cliente;
import br.acaitech.springMVC.FluxoDeVenda.entidades.ItemVenda;
import br.acaitech.springMVC.FluxoDeVenda.entidades.Produto;
import br.acaitech.springMVC.FluxoDeVenda.entidades.Venda;
import br.acaitech.springMVC.FluxoDeVenda.repository.ClienteRepository;
import br.acaitech.springMVC.FluxoDeVenda.repository.ItemVendaRepository;
import br.acaitech.springMVC.FluxoDeVenda.repository.VendaRepository;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemVendaRepository itemRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @GetMapping
    public ModelAndView listar(Venda venda) {
        List<Venda> vendaLista = vendaRepository.findAll();
        return new ModelAndView("carrinho/lista").addObject("carrinho", vendaLista);
    }

    @PostMapping("/vender")
    public ModelAndView vender(
            @ModelAttribute() Venda v, RedirectAttributes redirectAttributes) {
        v.setVendido(Boolean.TRUE);
        vendaRepository.save(v);
        return new ModelAndView("redirect:/index");
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(
            @ModelAttribute() Produto produto, RedirectAttributes redirectAttributes) {
        List<ItemVenda> listaItem = itemRepository.findAll();
        for (ItemVenda itemVenda : listaItem) {
            if (Objects.equals(itemVenda.getProduto().getId(), produto.getId())) {
                redirectAttributes.addFlashAttribute("mensagemFalha", "Produto já se encontra no carrinho");
                return new ModelAndView("redirect:/carrinho");
            } else {
                ItemVenda item = new ItemVenda(produto, 1, produto.getPrecoVenda());
                itemRepository.save(item);
                Cliente c = new Cliente();
                c.setNome("Fernando Lima Coutinho");
                c.setCpf(BigInteger.valueOf(37818748857l));
                clienteRepository.save(c);
                Venda v = new Venda(itemVenda, c, Boolean.FALSE);
                vendaRepository.save(v);
                redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto " + produto.getNome() + " adicionado com sucesso");
            }
        }
        return new ModelAndView("redirect:/carrinho");
    }

    @PostMapping("/salvar/mais/{id}")
    public ModelAndView salvarMais(
            @PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {
        ItemVenda i = itemRepository.findById(id);
        List<Venda> listaVenda = vendaRepository.findAll();
        Long idvenda = null;
        for (Venda v : listaVenda) {
            if(Objects.equals(v.getItemVenda().getId(), i.getId())){
                idvenda = v.getId();
            }
        }
        if(idvenda != null){
        Venda v = vendaRepository.getOne(idvenda.intValue());
        i = v.getItemVenda();
        int a = v.getItemVenda().getQuantidade();
        int estoque = v.getItemVenda().getProduto().getQuantidade();
        Integer qtd = v.getItemVenda().getQuantidade();
        Double aux = qtd.doubleValue();
        BigDecimal preco = v.getItemVenda().getPreçoItem();
        BigDecimal multiplicador = BigDecimal.valueOf(aux + 1);
        if (qtd < estoque) {
            i.setQuantidade(a + 1);
            preco.multiply(multiplicador);
            i.setPreçoItem(preco);
            v.setItemVenda(i);
            vendaRepository.save(v);
        }
        if (a == estoque) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Limite máximo atingido, você já está levando todo o nosso estoque!");
        }
        } else {
            redirectAttributes.addFlashAttribute("mensagemFalha", "Erro. Carrinho não existe");
        }
        return new ModelAndView("redirect:/carrinho");
    }

    @PostMapping("/salvar/menos/{id}")
    public ModelAndView salvarMenos(
            @PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {
        ItemVenda i = itemRepository.findById(id);
        List<Venda> listaVenda = vendaRepository.findAll();
        Long idvenda = null;
        for (Venda v : listaVenda) {
            if(Objects.equals(v.getItemVenda().getId(), i.getId())){
                idvenda = v.getId();
            }
        }
        if(idvenda != null){
        Venda v = vendaRepository.getOne(idvenda.intValue());
        i = v.getItemVenda();
        Integer qtd = v.getItemVenda().getQuantidade();
        Double aux = qtd.doubleValue();
        BigDecimal preco = v.getItemVenda().getPreçoItem();
        BigDecimal multiplicador = BigDecimal.valueOf(aux - 1);

        if (qtd > 1) {
            i.setQuantidade(qtd - 1);
            preco.multiply(multiplicador);
            i.setPreçoItem(preco);
            v.setItemVenda(i);
            vendaRepository.save(v);
        }
        if (aux == 1) {
            redirectAttributes.addFlashAttribute("mensagemFalha", "Para remover, utilize o botão com icone de lixo!");
        }
        } else {
            redirectAttributes.addFlashAttribute("mensagemFalha", "Erro. Carrinho não existe");
        }
        return new ModelAndView("redirect:/carrinho");
    }

    @PostMapping("/remover/{id}")
    public ModelAndView remover(
            @PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {
        vendaRepository.deleteById(id.intValue());
        redirectAttributes
                .addFlashAttribute("mensagemSucesso", "Produto removido do carrinho com sucesso");
        return new ModelAndView("redirect:/carrinho");
    }

    /*ItemVenda i = carrinho.getItens();
        int a = carrinho.getItens().getQuantidade();
        int estoque = carrinho.getProduto().getQuantidade();
        if(a <= estoque){
        i.setQuantidade(a + 1);
        carrinho.setItens(i);
        carrinhoRepository.save(carrinho);
        redirectAttributes.addFlashAttribute("mensagemSucesso","Quantidade alterada com sucesso");
        return new ModelAndView("redirect:/carrinho");
        } else {
            redirectAttributes.addFlashAttribute("mensagemFalha","Quantidade máxima atingida, está comprando todo o nosso estoque!");
        return new ModelAndView("redirect:/carrinho"); 
        }
    }*/
 /*@GetMapping("/adicionar/{id}")
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
                List<CarrinhoVenda> carCheck = carrinhoVendaRepository.find();
                for (CarrinhoVenda carrinhoVenda : carCheck) {
                    if (!carrinhoVenda.getId().equals(idItem)) {
                        CarrinhoVenda carrinho = new CarrinhoVenda(itens);
                        carrinhoVendaRepository.save(carrinho);
                    }
                }
            }
            redirectAttributes.addFlashAttribute("mensagemSucesso", item.getProduto().getNome() + "" + " adicionado ao carrinho com sucesso.");
            return new ModelAndView("redirect:/carrinho/construir/{id}").addObject("idItem", idItem);
        } else {
            redirectAttributes.addFlashAttribute("mensagemFalha", "Erro. Produto não pode ser adicionado ao carrinho.");
            return new ModelAndView("redirect:/carrinho/construir");
        }
    }

    @GetMapping("/construir/{id}")
    public ModelAndView construirCarrinho(
            @PathVariable(name = "id") Long id, Long idItem,
            RedirectAttributes redirectAttributes) {
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
                if (Objects.equals(carrinho1.getProduto().getId(), carrinho.getProduto().getId())) {
                    redirectAttributes.addFlashAttribute("mensagemFalha", "Erro. Produto já se encontra no carrinho.");
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
    }*/
}
