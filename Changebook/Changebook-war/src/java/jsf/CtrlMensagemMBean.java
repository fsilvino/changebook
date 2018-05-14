/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.Livro;
import ejb.MensagemFachada;
import java.io.Serializable;
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
@Named(value = "ctrlMensagemMBean")
@ViewScoped
public class CtrlMensagemMBean implements Serializable {
    @EJB
    private MensagemFachada mensagemFachada;
    
    //@ManagedProperty(value = "usuarioMBean")
    @Inject private UsuarioMBean usuarioMBean;
    
    private Livro livro;
    private String conteudo;

    public CtrlMensagemMBean() {
    }

    public Livro getLivro() {
        return livro;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
    
    public void enviarMensagem() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            mensagemFachada.incluir(usuarioMBean.getUsuario(), conteudo, livro);
            FacesMessage msgf = new FacesMessage("Sucesso!", "Mensagem enviada.");
            context.addMessage(null, msgf);
            RequestContext.getCurrentInstance().execute("fechaCadastroMensagem()");
        } catch (Exception e) {
            FacesMessage msgf = new FacesMessage("Erro!", e.getMessage());
            context.addMessage(null, msgf);
        }
    }
    
    public void novaMensagem(Livro livro) {
        this.livro = livro;
        this.conteudo = "";
    }
    
}
