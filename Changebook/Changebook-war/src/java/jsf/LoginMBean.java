/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.UsuarioFachada;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;


/**
 *
 * @author Flávio
 */
@Named(value = "loginMBean")
@ViewScoped
public class LoginMBean implements Serializable {

    @EJB
    private UsuarioFachada usuarioFachada;
    
    @Inject
    private UsuarioMBean usuarioMBean;
    
    private String email;
    private String senha;

    /**
     * Creates a new instance of LoginMBean
     */
    public LoginMBean() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public boolean login() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (email.trim() == null || email.isEmpty() || senha.trim() == null || senha.isEmpty()) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:","Por favor, preencha os campos obrigatórios");
            context.addMessage(null, msg);
            return false;
        }
        try {
            usuarioFachada.validarLogin(email, senha);
            usuarioMBean.setUsuario(usuarioFachada.getUsuarioByEmail(email));
            RequestContext.getCurrentInstance().execute("PF('dlgLogin').hide()");
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro:", ex.getMessage());
            context.addMessage(null, msg);
        }
        
        return true;
    }
    
    public void logout() {
        this.email = "";
        this.senha = "";
        usuarioMBean.setUsuario(null);
        RequestContext.getCurrentInstance().execute("window.location.reload()");
    }
    
    public String getNomeUsuarioLogado() {
        if (usuarioMBean.getUsuario() != null) {
            return usuarioMBean.getUsuario().getNome();
        }
        return "";
    }
    
}
