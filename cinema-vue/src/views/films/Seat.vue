<script>
export default {
  props: {
    selectedSeats: Array,
    onSelect: Function
  },
  computed: {
    rowCount() {
      // Numero di righe della griglia dei posti
      return 10;
    },
    colCount() {
      // Numero di colonne della griglia dei posti
      return 12;
    }
  },
  methods: {
    isSelected(row, col) {
      // Verifica se il posto è già stato selezionato
      return this.selectedSeats.some(seat => seat.row === row && seat.col === col);
    },
    selectSeat(row, col) {
      // Emetti l'evento di selezione del posto
      this.$emit('onSelect', row, col);
    }
  }
};
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
      <div class="seat" v-for="col in colCount" :key="col" :class="{ selected: isSelected(row, col) }" @click="selectSeat(row, col)">
        {{ String.fromCharCode(65 + row - 1) }}{{ col }}
      </div>
    </div>
  </div>
</template>