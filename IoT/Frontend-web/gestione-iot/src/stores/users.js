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

  async function find(id) {
    user.value = await request('GET', `${baseUrl}/${id}`);
  }

  async function update(id) {
    user.value = await request('PUT', `${baseUrl}/${id}`, user.value);
  }

  async function remove(id) {
    await request('DELETE', `${baseUrl}/${id}`);
    users.value = users.value.filter(v => v.id !== id);
  }

  async function getAll() {
    users.value = await request('GET', `${baseUrl}`);
  }

  return { users, user, create, find, update, remove, getAll };
});