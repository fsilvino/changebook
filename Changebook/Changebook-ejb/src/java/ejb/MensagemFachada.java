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
public class MensagemFachada {

    @PersistenceContext(unitName = "Changebook-ejbPU")
    private EntityManager em;
    
    public void persist(Mensagem mensagem) {
        em.persist(mensagem);
    }
    
    // Metodo que retorna a lista de mensagens armazenada na tabela Mensagens
    public List<Mensagem> getListaMensagens() {
        Query query = em.createNamedQuery("Mensagem.findAll");
        return query.getResultList();
    }
    
    // Metodo que retorna as mensagens em que o usuario @param é destinatario 
    public List<ejb.Mensagem> getListaMensagensUsuario(Usuario usuario) {
        Query query = em.createNamedQuery("Mensagem.findByUsuario");
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }

    public void removeMensagensLivro(Livro livro) {
        for (Mensagem mensagem : livro.getMensagemList()) {
            remove(mensagem);
        }
    }
    
    public void remove(Mensagem mensagem) {
        em.remove(mensagem);
        em.flush();
    }
    
}
