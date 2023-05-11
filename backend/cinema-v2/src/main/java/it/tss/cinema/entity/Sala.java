package it.tss.cinema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
@NamedQueries({
        @NamedQuery(name = Sala.FIND_ALL, query = "select e from Sala e order by e.nome"),
})

@Entity
@Table(name = "sala")
public class Sala extends AbstractEntity {
    
    public static final String FIND_ALL = "Sala.findAll";
    
    @NotBlank
    @Column(nullable = false, unique = true)
    String nome;

    @Min(0)
    @Column(nullable = false)
    int posti;
    
    @Min(0)
    @Column(nullable = false)
    int post_x;    

    @Min(0)
    @Column(nullable = false)
    int post_y;   
    
    public Sala(){}

    public Sala(String nome, int posti, int post_x, int post_y) {
        this.nome = nome;
        this.posti = posti;
        this.post_x = post_x;
        this.post_y = post_y;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPosti() {
        return posti;
    }

    public void setPosti(int posti) {
        this.posti = posti;
    }

    public int getPost_x() {
        return post_x;
    }

    public void setPost_x(int post_x) {
        this.post_x = post_x;
    }

    public int getPost_y() {
        return post_y;
    }

    public void setPost_y(int post_y) {
        this.post_y = post_y;
    }
    
 

  
    
    
}