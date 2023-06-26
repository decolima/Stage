<script>
export default {
  props: {
    selectedSeats: Array,
    onSelect: Function,
    posti_x: { // Definizione della prop "posti_x" con tipo e richiesta obbligatoria
      type: Number,
      required: true
    },
    posti_y: { // Definizione della prop "posti_y" con tipo e richiesta obbligatoria
      type: Number,
      required: true
    }

  },
  data() { // Definizione del metodo "data" (Metodo che restituisce l'oggetto di dati)
    return {
      rowCount: this.posti_x, // Numero di righe
      colCount: this.posti_y, // Numero di colonne
      //selectedSeats: [], // Array di posti selezionati dall'utente 
      selectedSeatNumbers: [] // Array di numeri dei posti selezionati dall'utente
    };
  },
  computed: {

  },
  methods: { // Definizione dei metodi usati nel componente
    isSelected(row, col) { // Metodo per verificare se il posto è già stato selezionato
      return this.selectedSeats.some(seat => seat.row === row && seat.col === col);
    },
    isOccupied(row, col) {
  const selected = this.selectedSeats.some(seat => seat.row === row && seat.col === col);
  const current = this.selectedSeatNumbers.some(seatNumber => seatNumber === (row - 1) * this.colCount + col);
  const isUnavailable = this.unavailableSeats.some(seat => seat.row === row && seat.col === col);
  return selected || current || isUnavailable;
},

    selectSeat(row, col) { // Metodo per selezionare/deselezionare un posto
      const seat = { row, col };
      const index = this.selectedSeats.findIndex(s => s.row === row && s.col === col);
      if (index > -1) { // Se il posto era già stato selezionato, deselezioniamolo
        this.selectedSeats.splice(index, 1);
        this.selectedSeatNumbers.splice(index, 1);
        console.log('selectedSeats:', this.selectedSeats);
        this.$emit('onSelect', row, col); // Emettiamo l'evento "onSelect" all'esterno del componente
      } else { // Altrimenti selezioniamo il posto
        this.selectedSeats.push(seat);
        this.selectedSeatNumbers.push((row - 1) * this.colCount + col);
        console.log('selectedSeats:', this.selectedSeats);
        this.$emit('onSelect', row, col);  // Emettiamo l'evento "onSelect" all'esterno del componente
      }
      console.log('POST:', row, col); // Log del posto selezionato in console
    }
  }
}

</script>

<style>
.seat-map {
  display: flex;
  flex-direction: column;
}

.col {
  display: flex;
  justify-content: center;
}

.row {
  width: 35px;
  height: 40px;
  margin-right: 10px;
  margin-bottom: 10px;
  background-color: #e3e3e3;
  border-radius: 12px;
  text-align: center;
  line-height: 20px;
  font-weight: 700;
  cursor: pointer;
}

.seat {
  width: 50px;
  height: 50px;
  background-color: #ddd;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  cursor: pointer;
}

.selected {
  background-color: #f00;
  color: #fff;
}

.occupied {
  background-color: #f00;
  color: #fff;
}

.unavailable {
  background-color: #ccc;
  color: #000;
}

</style>

<template>
  <div class="seat-map">
    <div class="col" v-for="col in colCount" :key="col">
      <div class="row" v-for="row in rowCount" :key="row"
        :class="{ occupied: isOccupied(row, col), selected: isSelected(row, col), unavailable: isUnavailable(row, col) }"
        @click="selectSeat(row, col)">
        {{ String.fromCharCode(65 + row - 1) }}{{ col }}
      </div>
    </div>
  </div>
</template>


