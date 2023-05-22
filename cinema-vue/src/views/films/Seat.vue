<script >
export default {
  props: {
      seat: Object
  },
  methods: {
    selectSeat() {
      this.$emit('selected', this.seat);
    }
  }
}
</script>

<style>
.seat {
  width: 30px;
  height: 30px;
  background-color: white;
  border: 1px solid black;
  display: flex;
  justify-content: center;
  align-items: center;
}

.selected {
  background-color: green;
}

.occupied {
  background-color: red;
}
</style>

<template>
  <div class="seat" :class="{ selected: seat.selected, occupied: seat.occupied }"  @click="selectSeat">
    {{ seat.row }}{{ seat.number }}
  </div>
</template> 