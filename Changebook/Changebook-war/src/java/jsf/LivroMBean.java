/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.Livro;
import ejb.LivroFachada;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Flávio
 */
@Named(value = "livroMBean")
@ViewScoped
public class LivroMBean implements Serializable {

    @EJB
    private LivroFachada livroFachada;
    
    private Livro livro;
    
    @Inject private UsuarioMBean usuarioMBean;

    /**
     * Creates a new instance of LivroMBean
     */
    public LivroMBean() {
    }
    
    public List<Livro> getListaLivrosUsuario(){
        return livroFachada.getListaLivrosUsuario(usuarioMBean.getUsuario());
    }
    
    public List<Livro> getListaLivros() {
        return livroFachada.getListaLivros();
    }
    
    public void removerLivro(Livro livro) {
        livroFachada.remove(livro.getId());
    }

}
