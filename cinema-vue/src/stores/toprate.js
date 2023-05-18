import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { request } from "@/helpers";
import { config } from '@/app.config.js';

const baseUrl = `https://api.themoviedb.org/3/movie/now_playing?api_key=2ecbbf585e7fa616b14c37c3e817792f&language=it-IT`;

export const useTopRateStore = defineStore("topRates", () => {
  const topRates = ref([]);
  const TopRate = ref({});
  
  function $reset() {
    topRates.value = [];
    TopRate.value = {};
  }
  
  async function getAll() {
    topRates.value = await request('GET', `${baseUrl}`);
  }
 
  return {topRates, TopRate, $reset, getAll };
});