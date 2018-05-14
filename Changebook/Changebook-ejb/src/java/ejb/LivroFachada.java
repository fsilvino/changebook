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
    
    private void persist(Livro livro) {
        em.persist(livro);
    }
    
    public void incluir(String autor, String titulo, String sinopse, Usuario usuarioDono) throws Exception {
        Livro livro = new Livro();
        livro.setAutor(autor);
        livro.setTitulo(titulo);
        livro.setSinopse(sinopse);
        livro.setUsuario(usuarioDono);
        
        validarCadastro(livro);
        persist(livro);
    }
    
    
    private void validarCadastro(Livro livro) throws Exception {
        
        if (livro.getTitulo() == null || livro.getTitulo().isEmpty()) {
            throw new Exception("O campo Título é obrigatório!");
        }
        
        if (livro.getAutor() == null || livro.getAutor().isEmpty()) {
            throw new Exception("O campo Autor é obrigatório!");
        }
        
        if (livro.getUsuario() == null) {
            throw new Exception("É necessario estar logado para cadastrar um livro!");
        }
    }
    
    public void remove(int id) {
        Livro livroBd = em.find(Livro.class, id);
        mensagemFachada.removeMensagensLivro(livroBd);
        em.remove(livroBd);
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
