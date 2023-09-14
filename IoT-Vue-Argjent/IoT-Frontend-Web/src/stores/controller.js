import { ref } from 'vue';
import { defineStore } from 'pinia';
import { config } from '@/app.config.js';
import { request } from "@/helpers";

const baseUrl = `${config.baseUrl}/company/controller`;

export const useControllerStore = defineStore("controller", () => {
    const controllers = ref([]);
    const controller = ref({});

    async function getAll() {
        controllers.value = await request('GET', `${baseUrl}`);
    }

    return { controllers, controller, getAll }
  });
  