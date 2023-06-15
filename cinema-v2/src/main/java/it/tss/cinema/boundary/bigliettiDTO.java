package it.tss.cinema.boundary;

 

import it.tss.cinema.entity.Biglietto;
import it.tss.cinema.entity.Programmazione;
import it.tss.cinema.entity.Utente;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
/**
 *
 * @author filip
 */
public class bigliettiDTO {
    
    Programmazione programmazione;
    
    Utente Utente;
 
  
    @NotNull
    @Min(1)
    public int post_x;
    
    @NotNull
    @Min(1)
    public int post_y;  
    

   Biglietto.Tipo tipo_bigletto;  
   
   String nome_cliente;
   
 
   BigDecimal importo;
    
 
 
    //public List<Long> posti = new ArrayList<>();
    //public List<Long> sale_id = new ArrayList<>();    
    
    
}
