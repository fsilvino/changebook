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
public class UsuarioFechada {

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
    
}
