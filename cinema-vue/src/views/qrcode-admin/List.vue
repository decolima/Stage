<template>
  <div id="app1">
    <QRCodeReader />
  </div>
</template>

<script>
import QRCodeReader from "./QRCodeReader.vue";

export default {
  components: {
    QRCodeReader,
  },
};
</script>

<style>
#app1 {
  text-align: center;
  margin-top: 20px;
}
</style>
