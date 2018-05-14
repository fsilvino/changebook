/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
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
    
    @Inject
    private MensagemFachada mensagemFachada;
    
    public void persist(Livro livro) {
        em.persist(livro);
    }
    
    public void remove(Livro livro) {
        em.remove(em.find(Livro.class, livro.getId()));
    }
    
    // Metodo que retorna a lista de livros armazenada na tabela Livros
    public List<ejb.Livro> getListaLivros() {
        Query query = em.createNamedQuery("Livro.findAll");
        return query.getResultList();
    }
    
    // Metodo que retorn a lista de livros de determinado usuario
    public List<ejb.Livro> getListaLivrosUsuario(Usuario usuario) {
        Query query = em.createNamedQuery("Livro.findByProprietario");
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }
    
}
