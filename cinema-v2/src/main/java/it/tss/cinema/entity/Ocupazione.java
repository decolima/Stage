 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.tss.cinema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
/*
@NamedQueries({
        @NamedQuery(name = Ocupazione.FIND_ALL, query = "select e from Ocupazione e "),
           @NamedQuery(name = Ocupazione.FIND_BY_PROG,
            query = "select e from Ocupazione e where e.programmazione_id= :programmazione_id"),
})

@Entity
@Table(name = "ocupazione")
*/
public class Ocupazione extends AbstractEntity {
    
    public static final String FIND_ALL = "Ocupazione.findAll";
    public static final String FIND_BY_PROG = "Ocupazione.findbyprog";
       
        
        @NotNull
        @ManyToOne(optional = false)
        @JoinColumn(name = "programmazione_id")
        Programmazione programmazione_id;   
            
        @NotNull
        @ManyToOne(optional = true)
        @JoinColumn(name = "utente_id")
        Utente utente_id; 
        
        @NotNull
        @Min(1)
        int pos_x;
        
        @NotNull
        @Min(1)
        int pos_y;
                        
        String nome_cliente;     
        
        
        @OneToOne
        @NotNull
        Biglietto tipo_bigleto;
        
 
        
        
        String numero_bibleto;

    public Ocupazione(Programmazione programmazione_id, Utente utente_id, int pos_x, int pos_y, Biglietto tipo_bigleto, String numero_bibleto, String nome_cliente) {
        this.programmazione_id = programmazione_id;
        this.utente_id = utente_id;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.tipo_bigleto = tipo_bigleto;
        this.numero_bibleto = numero_bibleto;
        this.nome_cliente = nome_cliente;
    }

    public Programmazione getProgrammazione_id() {
        return programmazione_id;
    }

    public void setProgrammazione_id(Programmazione programmazione_id) {
        this.programmazione_id = programmazione_id;
    }

    public Utente getUtente_id() {
        return utente_id;
    }

    public void setUtente_id(Utente utente_id) {
        this.utente_id = utente_id;
    }

    public int getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public Biglietto getTipo_bigleto() {
        return tipo_bigleto;
    }

    public void setTipo_bigleto(Biglietto tipo_bigleto) {
        this.tipo_bigleto = tipo_bigleto;
    }

    public String getNumero_bibleto() {
        return numero_bibleto;
    }

    public void setNumero_bibleto(String numero_bibleto) {
        this.numero_bibleto = numero_bibleto;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    
        

    
}
