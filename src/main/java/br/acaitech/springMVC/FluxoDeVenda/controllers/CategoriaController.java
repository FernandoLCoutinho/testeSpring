/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.acaitech.springMVC.FluxoDeVenda.controllers;

import br.acaitech.springMVC.FluxoDeVenda.entidades.Categoria;
import br.acaitech.springMVC.FluxoDeVenda.repository.CategoriaRepository;
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
 * @author fernando.tsuda
 */
@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository repository;

    @GetMapping
    public ModelAndView listar() {
        return new ModelAndView("categoria/lista").addObject("categoria", repository.findAll());
    }

    @GetMapping("/novo")
    public ModelAndView adicionarNovo() {
        return new ModelAndView("categoria/formulario")
                .addObject("categoria", new Categoria());
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(Categoria c, RedirectAttributes redirectAttributes) {
        repository.save(c);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Categoria " + c.getNome() + " salva com sucesso");
        return new ModelAndView("redirect:/categoria");
    }

    @GetMapping("{id}/editar")
    public ModelAndView editar(
            @PathVariable(name = "id") Integer id) {
        Categoria c = repository.findById(id);
        return new ModelAndView("categoria/formulario").addObject("categoria", c);
    }

    @PostMapping("/{id}/remover")
    public ModelAndView remover(
            @PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        repository.delete(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Categoria ID: " + id + " removido com sucesso");
        return new ModelAndView("redirect:/categoria");
    }

}
