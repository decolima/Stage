import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { request } from "@/helpers";
import { config } from '@/app.config.js';

const baseUrl = `${config.baseUrl}/films`;

export const useFilmsStore = defineStore("films", () => {
    const films = ref([]);
    const film = ref({});
    const progrs = ref([]);
    const progr = ref({});

    function $reset() {
        films.value = [];
        film.value = {};
        progrs.value = [];
        progr.value = {};
    }
    async function create() {
         
        await request('POST', `${baseUrl}`, film.value);
        film.value = {};
    }

    async function importar(newFilm) {
        console.log("create",newFilm)
        await request('POST', `${baseUrl}`, newFilm);
        newFilm.value = {};
    }    
    async function getAll() {
        films.value = await request('GET', `${baseUrl}`);
    }
    async function getById(id) {
        film.value = await request('GET', `${baseUrl}/${id}`);
    }
    async function update(id) {
        film.value = await request('PUT', `${baseUrl}/${id}`, film.value);
    }
    async function remove(id) {
        await request('DELETE', `${baseUrl}/${id}`);
        films.value = films.value.filter(v => v.id !== id);
    }
    
    //async function createProiezione(sala) {
    //}

    return { films, film, progrs, progr, $reset, create, getAll, getById, update, remove,importar };
});