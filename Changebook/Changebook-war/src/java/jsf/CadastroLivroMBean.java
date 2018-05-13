/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.Livro;
import ejb.LivroFachada;
import ejb.Usuario;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author diogo
 */
@Named(value="cadastroLivroMBean")
@ViewScoped
public class CadastroLivroMBean implements Serializable {

    @EJB
    private LivroFachada livroFachada;
    
    @Inject private LivroMBean livroMBean;
    @Inject private UsuarioMBean usuarioMBean;
    
    private String titulo;
    private String autor;
    private String sinopse;

    public CadastroLivroMBean() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
    
    public void cadastrar() {
    
        if (validarCadastro()) {
            FacesContext context = FacesContext.getCurrentInstance();
            
            Livro livro = new Livro();
            livro.setAutor(autor);
            livro.setTitulo(titulo);
            livro.setSinopse(sinopse);
            livro.setUsuario(usuarioMBean.getUsuario());
            
            try {
                livroFachada.persist(livro);
                FacesMessage msg = new FacesMessage("Sucesso!", "Livro cadastrado.");
                context.addMessage(null, msg);
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage("Erro no cadastro do livro!", e.getMessage());
                context.addMessage(null, msg);
            }            
        }
      
    }
    
    private boolean validarCadastro() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (titulo == null || titulo.isEmpty()) {
            FacesMessage msg = new FacesMessage("Erro!", "O campo Título é obrigatório!");
            context.addMessage(null,msg);
            return false;
        }
        
        if (autor == null || autor.isEmpty()) {
            FacesMessage msg = new FacesMessage("Erro!", "O campo Autor é obrigatório!");
            context.addMessage(null,msg);
            return false;
        }        
        return true;
    }
    
    public void limpar() {
        titulo = null;
        autor = null;
        sinopse = null;
    }
}
