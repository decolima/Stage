package it.tss.cinema.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

@NamedQueries({
    @NamedQuery(name = Programmazione.FIND_ALL, query = "select e from Programmazione e order by e.data_programmazione"),
    @NamedQuery(name = Programmazione.FIND_BY_DATA,
            query = "select e from Programmazione e where e.data_programmazione >= :data order by e.data_programmazione"),
    @NamedQuery(name = Programmazione.FIND_BY_FILM,
            query = "select e from Programmazione e where e.film.id= :film_id"),
        @NamedQuery(name = Programmazione.FIND_BY_FILM_AND_DATA,
            query = "select e from Programmazione e where e.film.id= :film_id and e.data_programmazione >= :data "),
        @NamedQuery(name = Programmazione.FIND_BY_ID,
            query = "select e from Programmazione e where e.id = :id ")
})
@Entity
@Table(name = "programmazione",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"film_id", "data_programmazione"})})
public class Programmazione extends AbstractEntity {

    public static final String FIND_ALL = "Programmazione.findAll";
    public static final String FIND_BY_DATA = "Programmazione.findByData";
    public static final String FIND_BY_FILM = "Programmazione.findByFilm";
    public static final String FIND_BY_FILM_AND_DATA = "Programmazione.findByFilmAndData";
    public static final String FIND_BY_ID = "Programmazione.findById";
    
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "sala_id")
    Sala sala;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "film_id")
    Film film;

    @NotNull
    @Column(nullable = false)
    @FutureOrPresent
    LocalDate data_programmazione;

    @NotNull
    @Column(precision = 4, scale = 2, nullable = false)
    BigDecimal prezzo;
    
    @NotNull
    @Column(nullable = false)
    @FutureOrPresent
    LocalDate data_pubblicazione;    

    public Programmazione() {
    }
    
      public Programmazione(Film film, LocalDate data_programmazione, BigDecimal prezzo) {
        this.film = film;
        this.data_programmazione = data_programmazione;
        this.prezzo = prezzo;
    }  

    public Programmazione(Film film, LocalDate data_programmazione, BigDecimal prezzo, Sala sala, LocalDate data_pubblicazione) {
        this.film = film;
        this.data_programmazione = data_programmazione;
        this.prezzo = prezzo;
        this.sala = sala;
        this.data_pubblicazione = data_pubblicazione;
    }
    
    public LocalDate getData_pubblicazione() {
        return data_pubblicazione;
    }   
    
    public void setData_pubblicazione(LocalDate data_pubblicazione) {
        this.data_pubblicazione = data_pubblicazione;
    }    

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public LocalDate getData_programmazione() {
        return data_programmazione;
    }

    public void setData_programmazione(LocalDate data_programmazione) {
        this.data_programmazione = data_programmazione;
    } 

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }
}