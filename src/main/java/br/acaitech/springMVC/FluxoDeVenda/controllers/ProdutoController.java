/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.controllers;

import br.acaitech.springMVC.FluxoDeVenda.entidades.Categoria;
import br.acaitech.springMVC.FluxoDeVenda.entidades.Produto;
import br.acaitech.springMVC.FluxoDeVenda.repository.CategoriaRepository;
import br.acaitech.springMVC.FluxoDeVenda.repository.ProdutoRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author fernando.tsuda
 */
@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

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
        return new ModelAndView("produto/lista").addObject("produtos", produtos);
    }

    @GetMapping("/novo")
    public ModelAndView adicionarNovo() {
        return new ModelAndView("produto/formulario").addObject("produto", new Produto());
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(
            @PathVariable(name = "id") Long id) {
        Produto p = produtoRepository.findById(id);

        // Mapeando os IDs das categorias para gerar o formulário com as opções marcadas
        Set<Integer> idsCategorias = new HashSet<>();
        for (Categoria cat : p.getCategorias()) {
            idsCategorias.add(cat.getId());
        }
        p.setIdsCategorias(idsCategorias);
        return new ModelAndView("produto/formulario").addObject("produto", p);
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute("produto") Produto produto,
            /*BindingResult bindingResult,*/ RedirectAttributes redirectAttributes) {
        if (produto.getId() == null) {
            produto.setDtCadastro(LocalDateTime.now());
        }
        if (produto.getIdsCategorias() != null && !produto.getIdsCategorias().isEmpty()) {
            Set<Categoria> categoriasSelecionadas = new HashSet<>();
            for (Integer idCat : produto.getIdsCategorias()) {
                Categoria cat = categoriaRepository.findById(idCat);
                // Criar a relacao bidirecional entre os objetos produto e categoria.
                cat.setProdutos(new HashSet<>(Arrays.asList(produto)));
                categoriasSelecionadas.add(cat);
            }
            produto.setCategorias(categoriasSelecionadas);
        }
        produtoRepository.save(produto);
        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Produto " + produto.getNome() + " salvo com sucesso");
        return new ModelAndView("redirect:/produto");
    }

    @PostMapping("/{id}/remover")
    public ModelAndView remover(
            @PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {
        produtoRepository.delete(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Produto ID " + id + " removido com sucesso");
        return new ModelAndView("redirect:/produto");
    }

    @ModelAttribute("categorias")
    public List<Categoria> getCategorias() {
        return categoriaRepository.findAll();
    }

}
