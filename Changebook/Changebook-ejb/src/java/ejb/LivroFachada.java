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
public class LivroFachada {

    @PersistenceContext(unitName = "Changebook-ejbPU")
    private EntityManager em;
    
    public void persist(Livro livro) {
        em.persist(livro);
    }
    
    // Metodo que retorna a lista de clientes armazenada na tabela Clientes
    public List<ejb.Livro> getListaLivros() {
        Query query = em.createNamedQuery("Livro.findAll");
        return query.getResultList();
    }
    
}
