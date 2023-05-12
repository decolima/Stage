import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { request } from "@/helpers";
import { config } from '@/app.config.js';

const baseUrl = `${config.baseUrl}/programmazioni`;

export const useProgrammazioneStore = defineStore("programmazione", () => {
  const progrs = ref([]);
  const progr = ref({});

  async function create(progr) {
    try {
      const result = await request('POST', `${baseUrl}`, progr);
      progrs.value.push(result);
      return result;
    } catch (error) {
      console.error(error);
      throw new Error('Impossibile creare la programmazione.');
    }
  }

  async function getAll() {
    try {
      progrs.value = await request('GET', `${baseUrl}`);
    } catch (error) {
      console.error(error);
      throw new Error('Impossibile recuperare l\'elenco delle programmazioni.');
    }
  }

  async function getById(id) {
    try {
      progr.value = await request('GET', `${baseUrl}/${id}`);
    } catch (error) {
      console.error(error);
      throw new Error('Impossibile recuperare la programmazione.');
    }
  }

  async function update(progr) {
    try {
      progr.value = await request('PUT', `${baseUrl}`, progr);
      return progr.value;
    } catch (error) {
      console.error(error);
      throw new Error('Impossibile aggiornare la programmazione.');
    }
  }

  async function remove(id) {
    try {
      await request('DELETE', `${baseUrl}/${id}`);
      progrs.value = progrs.value.filter(v => v.id !== id);
    } catch (error) {
      console.error(error);
      throw new Error('Impossibile eliminare la programmazione.');
    }
  }

  async function createProgrammazione(progr) {
    try {
      const result = await request('POST', baseUrl, progr);
      progrs.value.push(result);
      return result;
    } catch (error) {
      console.error(error);
      throw new Error('Impossibile creare la programmazione.');
    }
  }

  return { progrs, progr, create, getAll, getById, update, remove, createProgrammazione };
});

/*
async function getProgrammazione(id) {
  await getById(id);
  progrs.value = await request('GET', `${baseUrl}/${id}/programmazioni`);
}
 
async function createProgrammazione() {
  return await request('POST', `${baseUrl}/programmazione`);
}
*/