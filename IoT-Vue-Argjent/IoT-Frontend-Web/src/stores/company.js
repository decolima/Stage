import { ref } from 'vue';
import { defineStore } from 'pinia';
import { config } from '@/app.config.js';
import { request } from "@/helpers";

const baseUrl = `${config.baseUrl}/company`;

export const useCompanyStore = defineStore("company", () => {
    const companies = ref([]);
    const company = ref({});

    async function getAll() {
        companies.value = await request('GET', `${baseUrl}`);
    }

    return { companies, company, getAll }
  });
  