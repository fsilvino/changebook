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
import java.util.Calendar;
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
        if(validarMensagem()) {
            FacesContext context = FacesContext.getCurrentInstance();
            Mensagem msg = new Mensagem();
            msg.setUsuarioDestinatario(livro.getUsuario());
            msg.setUsuarioRemetente(usuarioMBean.getUsuario());
            msg.setTexto(conteudo);
            msg.setLivro(livro);
            msg.setDataHora(Calendar.getInstance().getTime());
            
            try {
                mensagemFachada.persist(msg);
                FacesMessage msgf = new FacesMessage("Sucesso!", "Mensagem enviada.");
                context.addMessage(null, msgf);
                
                RequestContext.getCurrentInstance().execute("fechaCadastroMensagem()");
            } catch (Exception e) {
                FacesMessage msgf = new FacesMessage("Erro no envio da mensagem!", e.getMessage());
                context.addMessage(null, msgf);
            }
            
        }
    }
    
    private boolean validarMensagem() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (usuarioMBean.getUsuario() == null) {
            FacesMessage msg = new FacesMessage("Erro!", "Você deve estar logado para enviar uma mensagem.");
            context.addMessage(null,msg);
            return false;
        }
        
        if (conteudo == null || conteudo.isEmpty()) {
            FacesMessage msg = new FacesMessage("Erro!", "A mensagem deve conter algum conteúdo.");
            context.addMessage(null,msg);
            return false;
        }
        
        if (livro == null) {
            FacesMessage msg = new FacesMessage("Erro!", "Campo livro vazio.");
            context.addMessage(null,msg);
            return false;
        }
        return true;
    }
    
    public void novaMensagem(Livro livro) {
        this.livro = livro;
        this.conteudo = "";
    }
    
}
