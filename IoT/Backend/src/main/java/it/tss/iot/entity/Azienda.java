package it.tss.iot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

 

@Entity
@Table(name = "azienda")
public class Azienda extends AbstractEntity {


    @NotBlank
    @Column(nullable = false)
    String nome;
    String responsabile;
    String email;

    public Azienda(String nome, String responsabile, String email) {
        this.nome = nome;
        this.responsabile = responsabile;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
   

    

}