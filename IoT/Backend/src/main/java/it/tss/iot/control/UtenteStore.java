package it.tss.iot.control;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import it.tss.iot.Control;
import it.tss.iot.entity.Utente;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Control
public class UtenteStore extends AbstractStore<Utente>{
    

    public UtenteStore() {
        super(Utente.class);
    }

    public Optional<Utente> findByUsrAndPwd(String usr, String pwd){
        try{
            
             String hashedPwd = hashPassword(pwd); // Criptografa a senha fornecida pelo usuário
             
              
             
            Utente found = em.createNamedQuery(Utente.FIND_BY_USR_PWD,Utente.class)
            .setParameter("usr", usr)
            .setParameter("pwd", hashedPwd)
            .getSingleResult();
            return Optional.of(found);
        }catch(NoResultException | NonUniqueResultException  ex){
            return Optional.empty();
        }
    }
    
       private String hashPassword(String plainPassword) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = md.digest(plainPassword.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedPassword) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    } catch (NoSuchAlgorithmException e) {
        // Trate a exceção adequadamente
        return null;
    } 
    } 


}
