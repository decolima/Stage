package it.tss.iot.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
 

@Entity
@Table(name = "asset")
public class Asset extends AbstractEntity {
 
        String identificatore;
        String tipo;
        String modelo;
        String marca;
       

    public Asset(String identificatore, String tipo, String modelo, String marca) {
        this.identificatore = identificatore;
        this.tipo = tipo;
        this.modelo = modelo;
        this.marca = marca;
    }

    public String getIdentificatore() {
        return identificatore;
    }

    public void setIdentificatore(String identificatore) {
        this.identificatore = identificatore;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

 
   
    
 

  
    
    
}