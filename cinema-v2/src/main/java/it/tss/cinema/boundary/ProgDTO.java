package it.tss.cinema.boundary;

 

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

 
public class ProgDTO {
    
    public Long film_id;
<<<<<<< Updated upstream
    
    @NotNull
    @Future
    public LocalDate data_programmazione;
    
    @NotNull
    @Future
    public LocalDate data_pubblicazione;
    
=======
    // public Long id;
     
    @NotNull
    @Future
    //public LocalDate il;
    public LocalDate data_programmazione;
    
>>>>>>> Stashed changes
    @NotNull
    @Min(1)
    public BigDecimal prezzo;
    
    @JsonbProperty(value = "tutte_sale")
    public boolean tutteSale = false;
<<<<<<< Updated upstream
=======
    //public List<Long> sale_id = new ArrayList<>();
>>>>>>> Stashed changes
    public List<Long> sala_id = new ArrayList<>();
}