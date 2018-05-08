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
    
    public void persist(Usuario usuario) {
        em.persist(usuario);
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
