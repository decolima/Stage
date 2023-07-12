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
    // Funzione eseguita dopo l'assemblaggio del componente
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
      for (let riga = 0; riga < this.posti_x; riga++) {
        for (let colonna = 0; colonna < this.posti_y; colonna++) {
          if (this.unavailableSeats) {
            return this.unavailableSeats.some(
              (seat) => seat.row === riga && seat.col === colonna
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


  background: #23c74a url('../../assets/posto.png');
  background-repeat: no-repeat;
  background-position: left top;
  background-size: cover;
  /* ou "contain" */

  width: 50px;
  height: 50px;
  margin-right: 10px;
  margin-bottom: 10px;
  border-radius: 6px;
}

.seat {
  background: #23c74a url(../../assets/posto.png) no-repeat left top;
  width: 50px;
  height: 50px;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  cursor: pointer;
}

.occupied {
  background: rgb(242, 255, 0) url('../../assets/posto.png');
  background-repeat: no-repeat;
  background-position: left top;
  background-size: cover;
  /* ou "contain" */
  color: #000;
  width: 50px;
  height: 50px;
}

/*
.occupied {
  background: #f00 url('../../assets/posto.png');
  background-repeat: no-repeat;
  background-position: left top;
  background-size: cover;
  /* ou "contain" 
  color: #fff;
}
*/
.unavailable {

  background: #f00 url('../../assets/posto.png');
  background-repeat: no-repeat;
  background-position: left top;
  background-size: cover;
  /* ou "contain" */
  width: 50px;
  height: 50px;
}
</style>

<template>
  <div class="columns is-mobile is-multiline is-centered">
    <div class="column is-2">
      <div
        style="background: #23c74a; width: 200px; border-radius: 6px; font-weight: 700; color: #000; padding: 6px; margin: 6px;">
        Disponibile</div>
    </div>
    <div class="column is-2">
      <div
        style="background:rgb(242, 255, 0); width: 200px; border-radius: 6px; font-weight: 700; color: #000; padding: 6px; margin: 6px;">
        Selezionato</div>
    </div>
    <div class="column is-2">
      <div
        style="background: #f00; width: 200px; border-radius: 6px; font-weight: 700; color: #000; padding: 6px; margin: 6px;">
        Occupato</div>
    </div>
  </div>

  <div class="seat-map">
    <div class="col" v-for="col in colCount" :key="col">
      <div class="row" v-for="row in rowCount" :key="row" :class="{
              occupied: isOccupied(row, col),
        selected: isSelected(row, col),
        unavailable: isUnavailable(row, col),
      }" @click="selectSeat(row, col)">
    </div>
  </div>
</div></template>
