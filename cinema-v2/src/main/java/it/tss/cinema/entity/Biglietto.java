package it.tss.cinema.entity;

import java.math.BigDecimal;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NamedQueries({
    @NamedQuery(name = Biglietto.FIND_BY_PROIEZIONE,
            query = "select e from Biglietto e where e.programmazione= :programmazione_id"),
    @NamedQuery(name = Biglietto.FIND_BY_PROIEZIONE_UTENTE,
            query = "select e from Biglietto e where e.programmazione = :programmazione_id and e.utente.id= :utente_id "),})

@Entity
@Table(name = "biglietto")/*,
       /* uniqueConstraints = {
            @UniqueConstraint(columnNames = {"programmazione_id", "utente_id", "tipo"})})*/
public class Biglietto extends AbstractEntity {

    public static final String FIND_BY_PROIEZIONE = "Biglietto.findByProgrammazione";
    public static final String FIND_BY_PROIEZIONE_UTENTE = "Biglietto.findByProgrammazioneUtente";

    public static final BigDecimal CENTO = new BigDecimal(100);

    public static enum Tipo {
        INTERO(0, 0), RIDOTTO(30, 14), OMAGGIO(100, 8);

        private int sconto;
        private int eta;

        private Tipo(int sconto, int eta) {
            this.sconto = sconto;
            this.eta = eta;
        }

        public int getSconto() {
            return sconto;
        }

        public int getEta() {
            return eta;
        }
    }

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "programmazione_id")
    Programmazione programmazione;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "utente_id")
    Utente utente;

    @NotNull
    @Enumerated(EnumType.STRING)
    Tipo tipo;

    @NotNull
    @Min(1)
    int pos_x;

    @NotNull
    @Min(1)
    int pos_y;

    String nome_cliente;

    public Biglietto() {
    }

    public Biglietto(Programmazione programmazione, Utente utente, Tipo tipo, int pos_x, int pos_y, String nome_cliente) {
        this.programmazione = programmazione;
        this.utente = utente;
        this.tipo = tipo;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.nome_cliente = nome_cliente;
    }



    public int getPos_x() {
        return pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    @JsonbTransient
    @AssertTrue(message = "dati biglietto non validi")
    public boolean isValid() {
        boolean invalidTipo = tipo != Tipo.INTERO && utente.eta() > tipo.eta;
        boolean invalidEta = programmazione
                .getFilm()
                .getEtaMinima() > utente.eta();
        return !invalidEta && !invalidTipo;
    }

    public BigDecimal getImporto() {
        BigDecimal sconto = programmazione
                .getPrezzo()
                .multiply(new BigDecimal(tipo.getSconto()))
                .divide(CENTO);
        BigDecimal prezzo = programmazione
                .getPrezzo()
                .subtract(sconto);
        return prezzo.multiply(new BigDecimal(1));
    }

    public Programmazione getProgrammazione() {
        return programmazione;
    }

    public Utente getUtente() {
        return utente;
    }

    public Tipo getTipo() {
        return tipo;
    }



}
