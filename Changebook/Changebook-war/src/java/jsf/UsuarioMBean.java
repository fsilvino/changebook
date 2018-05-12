/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.Usuario;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;


/**
 *
 * @author Flávio
 */
@Named(value = "usuarioMBean")
@ManagedBean(name = "usuarioMBean")
@SessionScoped
public class UsuarioMBean implements Serializable {
    
    private Usuario usuario;
    
    /**
     * Creates a new instance of UsuarioMBean
     */
    public UsuarioMBean() {
        usuario = null;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public boolean isLogado() {
        return usuario != null;
    }
    
}
