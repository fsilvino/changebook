/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.LivroFachada;
import java.io.Serializable;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

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
        
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            livroFachada.incluir(autor, titulo, sinopse, usuarioMBean.getUsuario());
            FacesMessage msg = new FacesMessage("Sucesso!", "Livro cadastrado.");
            context.addMessage(null, msg);
            RequestContext.getCurrentInstance().execute("fechaCadastroLivro()");
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage("Erro!", e.getMessage());
            context.addMessage(null, msg);
        }            
    }
    

    
    public void limpar() {
        titulo = null;
        autor = null;
        sinopse = null;
    }
    
    private void limparMensagens() {
        FacesContext context = FacesContext.getCurrentInstance();
        Iterator<FacesMessage> it = context.getMessages();
        while ( it.hasNext() ) {
            it.next();
            it.remove();
        }
    }
    
    public void abrirCadastroNovoLivro() {
        limpar();
        limparMensagens();
    }
    
}
