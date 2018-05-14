/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import java.util.Calendar;
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
    
    private void persist(Mensagem mensagem) {
        em.persist(mensagem);
    }
    
    public void incluir(Usuario usuarioRemetente, Usuario usuarioDestinatario, String conteudo, Livro livro) throws Exception {
        Mensagem msg = new Mensagem();
        msg.setUsuarioDestinatario(usuarioDestinatario);
        msg.setUsuarioRemetente(usuarioRemetente);
        msg.setTexto(conteudo);
        msg.setLivro(livro);
        msg.setDataHora(Calendar.getInstance().getTime());

        validarMensagem(msg);
        persist(msg);
    }
    
    private void validarMensagem(Mensagem msg) throws Exception {
              
        if (msg.getUsuarioRemetente() == null) {
            throw new Exception("Você deve estar logado para enviar uma mensagem.");
        }
        
        if (msg.getLivro() == null) {
            throw new Exception("Campo livro vazio.");
        }
        
        if (msg.getTexto() == null || msg.getTexto().isEmpty()) {
            throw new Exception("A mensagem deve conter algum conteúdo.");
        }
        
    }
    
    // Metodo que retorna a lista de mensagens armazenada na tabela Mensagens
    public List<Mensagem> getListaMensagens() {
        Query query = em.createNamedQuery("Mensagem.findAll");
        return query.getResultList();
    }
    
    // Metodo que retorna as mensagens em que o usuario participa
    public List<ejb.Mensagem> getListaMensagensUsuario(Usuario usuario) {
        Query query = em.createNamedQuery("Mensagem.findByUsuario");
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }
    
    // Método retorna os contatos recebidos para cada livro
    // Uma mensagem neste caso representa o contato em si e não a mensagem
    public List<ejb.Mensagem> getListaContatosPorLivroDoUsuario(Usuario usuarioLogado) {
        ArrayList<ejb.Mensagem> contatos = new ArrayList<>();
        
        Query query = em.createNamedQuery("Mensagem.findByUsuario");
        query.setParameter("usuario", usuarioLogado);
        List<ejb.Mensagem> mensagens = query.getResultList();
        int idUsuario = 0, idLivro = 0;
        
        for (Mensagem mensagem : mensagens) {
            if (!mensagem.getUsuarioRemetente().getId().equals(usuarioLogado.getId())) {
                if (!mensagem.getUsuarioRemetente().getId().equals(idUsuario) ||
                    !mensagem.getLivro().getId().equals(idLivro)) {
                    idUsuario = mensagem.getUsuarioRemetente().getId();
                    idLivro = mensagem.getLivro().getId();
                    contatos.add(mensagem);
                }
            }
        }
        
        return contatos;
    }
    
    public List<Mensagem> getListaMensagensUsuarioELivro(Usuario usuarioLogado, Usuario usuarioInteressado, Livro livro) {
        Query query = em.createNamedQuery("Mensagem.findByUsuarioELivro");
        query.setParameter("usuarioLogado", usuarioLogado);
        query.setParameter("usuarioInteressado", usuarioInteressado);
        query.setParameter("livro", livro);
        return query.getResultList();
    }

    public void removeMensagensLivro(Livro livro) {
        for (Mensagem mensagem : livro.getMensagemList()) {
            remove(mensagem);
        }
    }
    
    public void remove(Mensagem mensagem) {
        em.remove(mensagem);
    }
    
}
