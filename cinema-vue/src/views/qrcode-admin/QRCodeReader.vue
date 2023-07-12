<template>
  <div>
    <qrcode-stream @decode="handleDecode"></qrcode-stream>
    <div v-if="qrCodeContent" class="result">{{ qrCodeContent }}</div>
  </div>
</template>

<script>
import { QrcodeStream } from "vue-qrcode-reader";

export default {
  components: {
    QrcodeStream
  },
  data() {
    return {
      qrCodeContent: null
    };
  },
  methods: {
    handleDecode(content) {
      this.qrCodeContent = content;
    }
  }
};
</script>

<style scoped>
.result {
  margin-top: 20px;
  font-weight: bold;
}
</style>
