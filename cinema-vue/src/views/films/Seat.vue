<script>
export default {
  emits: ["onSelect"],
  props: {
    selectedSeats: Array,
    unavailableSeats: Array,
    onSelect: Function,
    posti_x: {
      type: Number,
      required: true,
    },
    posti_y: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      rowCount: this.posti_x,
      colCount: this.posti_y,
      selectedSeatNumbers: this.unavailableSeats,
    };
  },
  mounted() {
    // Função executada após o componente ser montado
    //this.updateUnavailableSeats();
  },
  methods: {
    isSelected(row, col) {
      return this.selectedSeats.some(
        (seat) => seat.row === row && seat.col === col
      );
    },
    isOccupied(row, col) {
      const selected = this.selectedSeats.some(
        (seat) => seat.row === row && seat.col === col
      );
      const current = this.selectedSeatNumbers.some(
        (seatNumber) => seatNumber === (row - 1) * this.colCount + col
      );

      return selected || current;
    },
    isUnavailable(row, col) {
      if (this.unavailableSeats) {
        return this.unavailableSeats.some(
          (seat) => seat.row === row && seat.col === col
        );
      }
      return false;
    },
    selectSeat(row, col) {
  const seat = { row, col };
  const index = this.selectedSeats.findIndex((s) => s.row === row && s.col === col);

  if (index > -1) {
    this.selectedSeats.splice(index, 1);
    const seatNumberIndex = this.selectedSeatNumbers.findIndex((number) => number === (row - 1) * this.colCount + col);
    if (seatNumberIndex > -1) {
      this.selectedSeatNumbers.splice(seatNumberIndex, 1);
    }
    this.$emit("onSelect", row, col);
  } else {
    this.selectedSeats.push(seat);
    this.selectedSeatNumbers.push((row - 1) * this.colCount + col);
    this.$emit("onSelect", row, col);
  }
},
    updateUnavailableSeats() {
      console.log(this.unavailableSeats);

      for (let linha = 0; linha < this.posti_x; linha++) {
        for (let coluna = 0; coluna < this.posti_y; coluna++) {
          if (this.unavailableSeats) {
            return this.unavailableSeats.some(
              (seat) => seat.row === linha && seat.col === coluna
            );
          }
        }
      }
    },
  },
};
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

  background: #e3e3e3 url('../../assets/posto.png');
  background-repeat: no-repeat;
  background-position: left top;
  background-size: cover; /* ou "contain" */

  width: 35px;
  height: 40px;
  margin-right: 10px;
  margin-bottom: 10px;

  border-radius: 12px;
  text-align: center;
  line-height: 20px;
  font-weight: 700;
  cursor: pointer;
}

.seat {
  background:#23c74a url(../../assets/posto.png) no-repeat left top;
  width: 50px;
  height: 50px;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  cursor: pointer;
}

.selected {
  background:rgb(111, 0, 255) url(../../assets/posto.png) no-repeat left top;
  color: #fff;
}

.occupied {
  background: #f00 url('../../assets/posto.png');
  background-repeat: no-repeat;
  background-position: left top;
  background-size: cover; /* ou "contain" */
  color: #fff;
}

.unavailable {
  background: #d7af1e url('../../assets/posto.png');
  background-repeat: no-repeat;
  background-position: left top;
  background-size: cover; /* ou "contain" */
}
</style>

<template>

  <div class="seat-map">
    <div class="col" v-for="col in colCount" :key="col">
      <div
        class="row"
        v-for="row in rowCount"
        :key="row"
        :class="{
          occupied: isOccupied(row, col),
          selected: isSelected(row, col),
          unavailable: isUnavailable(row, col),
        }"
        @click="selectSeat(row, col)"
      >
       
      </div>
    </div>
  </div>
</template>
