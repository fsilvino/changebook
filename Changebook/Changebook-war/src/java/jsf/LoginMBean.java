/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.Usuario;
import ejb.UsuarioFachada;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
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
    
    @ManagedProperty(value = "#{usuarioMBean}")
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
        
        Usuario user = usuarioFachada.getUsuarioByEmail(email);
        
        if (user == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "E-mail não cadastrado!"));
            return false;
        }
        
        if (!user.getSenha().equals(senha)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Senha incorreta!"));
            return false;
        }
        
        usuarioMBean.setUsuario(user);
        
        return true;
    }
    
}
