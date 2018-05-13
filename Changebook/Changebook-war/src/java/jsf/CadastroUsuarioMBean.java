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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.util.List;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Flávio
 */
@Named(value = "cadastroUsuarioMBean")
@ViewScoped
public class CadastroUsuarioMBean implements Serializable {

    @EJB
    private UsuarioFachada usuarioFachada;
    
    //@ManagedProperty(value = "usuarioMBean")
    @Inject private UsuarioMBean usuarioMBean;
    
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    
    /**
     * Creates a new instance of CadastroUsuarioMBean
     */
    public CadastroUsuarioMBean() {
        
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public boolean cadastrar() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        List<FacesMessage> messageList = context.getMessageList();
        if (!messageList.isEmpty()) { 
            messageList.clear();
        }
        if (!validarCadastro()) {
            return false;
        }
        
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setTelefone(telefone);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        try {
            usuarioFachada.persist(usuario);
            usuarioMBean.setUsuario(usuario);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cadastro realizado com sucesso!"));
            
            RequestContext.getCurrentInstance().execute("zeraEFechaCadastroUsuario()");
            
            return true;
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", e.getMessage()));
            return false;
        }
        
    }
    
    private boolean validarCadastro() {
        boolean valido = true;
        
        FacesContext context = FacesContext.getCurrentInstance();
        if (nome == null || nome.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "O campo Nome é obrigatório!"));
            return false;
        }
        
        if (telefone == null || telefone.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "O campo Telefone é obrigatório!"));
            valido = false;
        }
        
        if (email == null || email.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "O campo E-mail é obrigatório!"));
            valido = false;
        } else if (usuarioFachada.getUsuarioByEmail(email) != null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Já existe um usuário cadastrado com o e-mail informado!"));
            valido = false;
        }
        
        if (senha == null || senha.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "O campo Senha é obrigatório!"));
            valido = false;
        }
        
        return valido;
    }
    
}
