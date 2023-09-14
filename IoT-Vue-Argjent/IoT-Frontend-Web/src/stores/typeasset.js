import { ref } from 'vue';
import { defineStore } from 'pinia';
import { config } from '@/app.config.js';
import { request } from "@/helpers";

const baseUrl = `${config.baseUrl}/asset/typeasset`;

export const useTypeAssetStore = defineStore("typeasset", () => {
    const typeassets = ref([]);
    const typeasset = ref({});

    async function getAll() {
        typeassets.value = await request('GET', `${baseUrl}`);
    }

    return { typeassets, typeasset, getAll }
  });