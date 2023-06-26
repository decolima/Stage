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
    },
    unavailableSeats: {
      type: Array,
      default: () => [] // Define um valor padrão vazio para evitar o erro
    }
  },
  data() {
    return {
      rowCount: this.posti_x,
      colCount: this.posti_y,
      selectedSeatNumbers: []
    };
  },
  methods: {
    isSelected(row, col) {
      return this.selectedSeats.some(seat => seat.row === row && seat.col === col);
    },
    isOccupied(row, col) {
      const selected = this.selectedSeats.some(seat => seat.row === row && seat.col === col);
      const current = this.selectedSeatNumbers.some(seatNumber => seatNumber === (row - 1) * this.colCount + col);

      return selected || current;
    },
    isUnavailable(row, col) {
      if (this.unavailableSeats) {
        return this.unavailableSeats.some(seat => seat.row === row && seat.col === col);
      }
      return false;
    },
    selectSeat(row, col) {
      const seat = { row, col };
      const index = this.selectedSeats.findIndex(s => s.row === row && s.col === col);
      if (index > -1) {
        this.selectedSeats.splice(index, 1);
        this.selectedSeatNumbers.splice(index, 1);
        this.$emit('onSelect', row, col);
      } else {
        this.selectedSeats.push(seat);
        this.selectedSeatNumbers.push((row - 1) * this.colCount + col);
        this.$emit('onSelect', row, col);
      }
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
  background-color: #23c74a;
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
  background-color: #e3e3e3;
  color: #f00;
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

