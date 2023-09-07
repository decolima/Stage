import { ref } from 'vue';
import { defineStore } from 'pinia';
import { config } from '@/app.config.js';
import { request } from "@/helpers";

const baseUrl = `${config.baseUrl}/asset/monitoring`;

export const useAssetMonitoringStore = defineStore("assetMonitoring", () => {
    const assetsMonitoring = ref([]);
    const assetMonitoring = ref({});

    async function getAll() {
        assetsMonitoring.value = await request('GET', `${baseUrl}`);
    }

    return { assetsMonitoring, assetMonitoring, getAll }
  });