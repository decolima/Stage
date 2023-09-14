import { ref } from 'vue';
import { defineStore } from 'pinia';
import { config } from '@/app.config.js';
import { request } from "@/helpers";

const baseUrl = `${config.baseUrl}/tag`;

export const useTagStore = defineStore("tag", () => {
    const tags = ref([]);
    const tag = ref({});

    async function getAll() {
        tags.value = await request('GET', `${baseUrl}`);
    }

    return { tags, tag, getAll }
  });
  