import { ref } from 'vue';
import { defineStore } from 'pinia';
import { config } from '@/app.config.js';
import { request } from "@/helpers";

const baseUrl = `${config.baseUrl}/asset`;

export const useAssetStore = defineStore("assets", () => {
    const assets = ref([]);
    const asset = ref({});

    async function getAll() {
        assets.value = await request('GET', `${baseUrl}`);
    }

    return { assets, asset, getAll }
  });