/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.Livro;
import ejb.LivroFachada;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Flávio
 */
@Named(value = "livroMBean")
@RequestScoped
public class LivroMBean {

    @EJB
    private LivroFachada livroFachada;

    /**
     * Creates a new instance of LivroMBean
     */
    public LivroMBean() {
    }

    public List<Livro> getListaLivros() {
        return livroFachada.getListaLivros();
    }

}
