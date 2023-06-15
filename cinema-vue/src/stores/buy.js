import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { request } from "@/helpers";
import { config } from '@/app.config.js';

const baseUrl = `${config.baseUrl}/biglietti`;

export const useTKTStore = defineStore("biglietto", () => {
    const progrs = ref([]);
    const progr = ref({});
    const tkts = ref([]);
    const tkt = ref({});

    const Tipo = Object.freeze({
        INTERO: 'INTERO',
        RIDOTTO: 'RIDOTTO',
        OMAGGIO: 'OMAGGIO'
    });

    tkt.value.tipo = [Tipo.INTERO, Tipo.RIDOTTO, Tipo.OMAGGIO];

    function $reset() {
        progrs.value = [];
        progr.value = {};
        tkts.value = [];
        tkt.value = {};
    }
    /*
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
    async function getProgrammazione(id) {
        await getById(id);
        progrs.value = await await request('GET', `${baseUrl}/${id}/programmazioni`);
    }
    async function createProgrammazione(id) {
        progr.value = request('POST', `${baseUrl}/${id}/programmazioni`, progr.value);
        progr.value = {};
    }
    async function getByPosti() {
        tkts.value = await request('GET', `${baseUrl}/programmazione/${id}`);
    }
    */
    async function getAll() {
        tkts.value = await request('GET', `${baseUrl}`);
    }
    async function getById(id) {
        tkt.value = await request('GET', `${baseUrl}/${id}`);
    }
    async function deleteById(id) {
        await request('DELETE', `${baseUrl}/${id}`);
        tkts.value = tkts.value.filter(v => v.id !== id);
    }
    async function update(id) {
        tkt.value = await request('PUT', `${baseUrl}/${id}`, tkt.value);
    }
    async function getByProgrammazioneId(programmazione_id) {
        progrs.value = await request('GET', `${baseUrl}/programmazione/${programmazione_id}`);
    }
    /*
    async function create(tkt) {
        await request('POST', `${baseUrl}`, tkt);
        //tkt.value = {};
    }
    */
    async function create(tkt) {
        try {
          const result = await request('POST', `${baseUrl}`, tkt);
          tkts.value.push(result);
          return result;
        } catch (error) {
          console.error(error);
          throw new Error('Impossibile creare il biglietto.');
        }
      }
      
    async function createTkt(tkt) {
        try {
            const result = await request('POST', `${baseUrl}`, tkt, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            tkts.value.push(result);
            return result;
        } catch (error) {
            console.error(error);
            throw new Error('Impossibile creare il biglietto.');
        }
    }

    return { tkts, tkt, progr, progrs, Tipo, $reset, getAll, getById, deleteById, update, getByProgrammazioneId, createTkt, create };
});
   // return { films, film, progrs, progr, tkts, tkt, $reset, create, getAll, getById, update, remove, getProgrammazione, createProgrammazione, getByPosti };
//});

