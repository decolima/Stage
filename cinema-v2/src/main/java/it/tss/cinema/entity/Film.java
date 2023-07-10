package it.tss.cinema.entity;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NamedQueries({
        @NamedQuery(name = Film.FIND_ALL, query = "select e from Film e order by e.titolo"),
    @NamedQuery(name = Film.FIND_BY_ID, query = "select e from Film e where e.id=:id"),
})

@Entity
@Table(name = "film")
public class Film extends AbstractEntity {

    public static final String FIND_ALL = "Film.findAll";
    public static final String FIND_BY_ID = "Film.findById"; 

    @NotBlank
    @Column(nullable = false)
    String titolo;

    String descrizione;

    String regista;
    
    String cartellone;
    
    

    @JsonbProperty("eta_minima")
    @Column(name = "eta_minima", nullable = false)
    @Min(0)
    int etaMinima;

    public Film() {
    }

    public Film(String titolo, String descrizione, String regista, int etaMinima, String cartellone) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.regista = regista;
        this.etaMinima = etaMinima;
        this.cartellone = cartellone;
    }

    public String getCartellone() {
        return cartellone;
    }
    
    public void setCartellone(String cartellone) {
        this.cartellone = cartellone;
    }    
        
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getRegista() {
        return regista;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public int getEtaMinima() {
        return etaMinima;
    }

    public void setEtaMinima(int etaMinima) {
        this.etaMinima = etaMinima;
    }

}