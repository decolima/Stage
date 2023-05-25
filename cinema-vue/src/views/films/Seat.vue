<script>
export default {
  props: {
    selectedSeats: Array,
    onSelect: Function,
    posti_x: {
    type: Number,
    required: true
  },
  posti_y: {
    type: Number,
    required: true
  }


  },
  data() {
    return {
      rowCount: this.posti_x,
      colCount: this.posti_y,    
      selectedSeats: [],
      selectedSeatNumbers: []
    };
  },
  computed: {
    
  },
  methods: {
    isSelected(row, col) {
      // Verifica se il posto è già stato selezionato
      return this.selectedSeats.some(seat => seat.row === row && seat.col === col);
    },
    isOccupied(row, col) {
      const selected = this.selectedSeats.some(seat => seat.row === row && seat.col === col);
      const current = this.selectedSeatNumbers.some(seatNumber => seatNumber === (row - 1) * this.colCount + col);
      return selected || current;
    },
    selectSeat(row, col) {
      const seat = { row, col };
      const index = this.selectedSeats.findIndex(s => s.row === row && s.col === col);
      if (index > -1) {
        // Il posto era già stato selezionato, deselezioniamolo
        this.selectedSeats.splice(index, 1);
        this.selectedSeatNumbers.splice(index, 1);
        this.$emit('onSelect', row, col); // Aggiungiamo l'emissione dell'evento
      } else {
        // Il posto non era ancora stato selezionato, selezioniamolo
        this.selectedSeats.push(seat);
        this.selectedSeatNumbers.push((row - 1) * this.colCount + col);
        this.$emit('onSelect', row, col); // Aggiungiamo l'emissione dell'evento
      }
    }
  }
}
/*
selectSeat(row, col) {
  if (!this.isSelected(row, col) && !this.isOccupied(row, col)) { // Verifica se il posto non è già stato selezionato e non è occupato
    this.selectedSeats.push({ row: row, col: col }); // Aggiungiamo un oggetto al posto di due valori singoli
    this.selectedSeatNumbers.push((row - 1) * this.colCount + col);
    this.$emit('onSelect', row, col); // Aggiungiamo l'emissione dell'evento
  }
},
*/
/*
export default {
  props: {
    selectedSeats: Array,
  },
  data() {
    return {
      seats: [],
    };
  },
  methods: {
    selectSeat(row, col) {
      // Verifica se il posto è già stato selezionato
      const seatIndex = this.seats.findIndex(seat => seat.row === row && seat.col === col);
      if (seatIndex >= 0) {
        // Rimuovi il posto selezionato dalla lista dei posti
        this.seats.splice(seatIndex, 1);
      } else {
        // Aggiungi il posto selezionato alla lista dei posti
        this.seats.push({ row, col });
      }
      // Emetti l'evento di selezione del posto
      this.$emit('selectSeat', this.seats.map(seat => seat.row + seat.col));
    },
    isSelected(row, col) {
      // Verifica se il posto è già stato selezionato
      return this.selectedSeats.some(seat => seat.row === row && seat.col === col);
    },
  }
};
*/
</script>

<style>
.seat-map {
  display: flex;
  flex-direction: column;
}

.row {
  display: flex;
  justify-content: center;
}

.seat {
  width: 30px;
  height: 30px;
  margin-right: 5px;
  margin-bottom: 5px;
  background-color: #ccc;
  border-radius: 4px;
  text-align: center;
  line-height: 30px;
  font-weight: bold;
  cursor: pointer;
}

.selected {
  background-color: #f00;
  color: #fff;
}
</style>

<template>
  <div class="seat-map">
    <div class="row" v-for="row in rowCount" :key="row">
      <div class="seat" v-for="col in colCount" :key="col"
        :class="{ selected: isSelected(row, col) || isOccupied(row, col) }" @click="selectSeat(row, col)">
        {{ String.fromCharCode(65 + row - 1) }}{{ col }}
      </div>
    </div>
  </div>
</template>