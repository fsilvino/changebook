/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.Livro;
import ejb.Mensagem;
import ejb.MensagemFachada;
import ejb.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author diogo
 */
@Named(value = "mensagemMBean")
@ViewScoped
public class MensagemMBean implements Serializable {
    
    @EJB
    private MensagemFachada mensagemFachada;
    
    private Mensagem mensagem;
    
    private Livro livroVerMensagens; 
    private Usuario usuarioInteressadoVerMensagens;
    
    
    @Inject 
    private UsuarioMBean usuarioMBean;

    public MensagemMBean() {
    }
    
    public List<Mensagem> getListaMensagensUsuario(){
        return mensagemFachada.getListaMensagensUsuario(usuarioMBean.getUsuario());
    }
    
    public List<Mensagem> getListaContatosPorLivroDoUsuario(){
        return mensagemFachada.getListaContatosPorLivroDoUsuario(usuarioMBean.getUsuario());
    }
    
    public void verConversaLivroUsuarioInteressado(Livro livro, Usuario usuarioInteressado) {
        this.livroVerMensagens = livro;
        this.usuarioInteressadoVerMensagens = usuarioInteressado;
    }
    
    public List<Mensagem> getListaMensagensLivroEUsuarioInteressado() {
        if (this.usuarioInteressadoVerMensagens != null && this.livroVerMensagens != null && this.usuarioMBean.getUsuario() != null) {
            return mensagemFachada.getListaMensagensUsuarioELivro(usuarioMBean.getUsuario(), usuarioInteressadoVerMensagens, livroVerMensagens);
        }
        return new ArrayList<>();
    }
    
    public Usuario getUsuarioInteressado() {
        return usuarioInteressadoVerMensagens;
    }
    
    public Livro getLivroVerMensagens() {
        return livroVerMensagens;
    }
    
}
