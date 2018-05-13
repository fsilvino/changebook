/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.Mensagem;
import ejb.MensagemFachada;
import java.io.Serializable;
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
    
    @Inject 
    private UsuarioMBean usuarioMBean;

    public MensagemMBean() {
    }
    
    public List<Mensagem> getListaMensagensUsuario(){
        return mensagemFachada.getListaMensagensUsuario(usuarioMBean.getUsuario());
    }
    
}
