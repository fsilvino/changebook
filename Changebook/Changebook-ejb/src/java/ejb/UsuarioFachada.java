/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Flávio
 */
@Stateless
@LocalBean
public class UsuarioFachada {

    @PersistenceContext(unitName = "Changebook-ejbPU")
    private EntityManager em;
    
    private void persist(Usuario usuario) {
        em.persist(usuario);
    }
    
    public void incluir(String nome, String telefone, String email, String senha) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setTelefone(telefone);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        
        validarCadastro(usuario);
        persist(usuario);
    }
    
    private void validarCadastro(Usuario usuario) throws Exception {

        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new Exception("O campo Nome é obrigatório!");
        }
        
        if (usuario.getTelefone() == null || usuario.getTelefone().isEmpty()) {
            throw new Exception("O campo Telefone é obrigatório!");
        }
        
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new Exception("O campo E-mail é obrigatório!");
        } else if (getUsuarioByEmail(usuario.getEmail()) != null) {
            throw new Exception("Já existe um usuário cadastrado com o e-mail informado!");
        }
        
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new Exception("O campo Senha é obrigatório!");
        }
    }
    
    public void validarLogin(String email, String senha) throws Exception {
        Usuario user = getUsuarioByEmail(email);
        
        if (user == null) {
            throw new Exception("E-mail não cadastrado!");
        }
        
        if (!user.getSenha().equals(senha)) {
            throw new Exception("Senha incorreta!");
        }
        
    }
    
    
    // Metodo que retorna a lista de clientes armazenada na tabela Clientes
    public List<Usuario> getListaUsuarios() {
        Query query = em.createNamedQuery("Usuario.findAll");
        return query.getResultList();
    }
    
    public Usuario getUsuarioByEmail(String email) {
        Query query = em.createNamedQuery("Usuario.findByEmail");
        query.setParameter("email", email);
        List resultado = query.getResultList();
        if (!resultado.isEmpty()) {
            return (Usuario) resultado.get(0);
        }
        return null;
    }
    
}
