<script >
export default {
  props: {
    seat: {
      type: Object,
      required: true,
    },
  },
  methods: {
    selectSeat() {
      this.$emit('selected', this.seat);
    },
  },
};
</script>

<template>
  <div class="seat" :class="{ 'is-available': seat.isAvailable, 'is-selected': seat.isSelected }" @click="selectSeat">
    {{ seat.row }}{{ seat.number }}
  </div>
</template>