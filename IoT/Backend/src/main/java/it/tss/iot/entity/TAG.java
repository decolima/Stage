package it.tss.iot.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

 
@Entity
@Table(name = "tag")
public class TAG extends AbstractEntity {


    @NotNull
    String indirezzo;


    @NotNull
    @Min(0)
    int status;

    public TAG(String indirezzo, int status) {
        this.indirezzo = indirezzo;
        this.status = status;
    }

    public String getIndirezzo() {
        return indirezzo;
    }

    public void setIndirezzo(String indirezzo) {
        this.indirezzo = indirezzo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
 

}
