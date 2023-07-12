import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { request } from "@/helpers";
import { config } from '@/app.config.js';

const baseUrl = `${config.baseUrl}/users`;

export const useUsersStore = defineStore("users", () => {
  const users = ref({});
  const user = ref({});

  async function create(user) {
      return await request('POST',`${baseUrl}`, user);
  }

  return { users, user, create };
});