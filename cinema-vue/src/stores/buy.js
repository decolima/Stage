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
    const occupati = ref([]);
    const occupato = ref({});

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
         occupati.value = [];
         occupato.value = {};

    }

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
    async function getByProgrammazioneId(id) {
        

        try {
            occupati.value = await request('GET', `${baseUrl}/programmazione/${id}`);
            console.log('occupato: ', occupati.value);
          } catch (error) {
            console.info("ERRO");
          }
        
        }
    
    async function create(tkt) {
        console.info(tkt)
        return await request('POST',`${baseUrl}`, tkt);
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

    return { tkts, tkt, progr, progrs, Tipo, occupati,occupato, $reset, getAll, getById, deleteById, update, getByProgrammazioneId, createTkt, create };
});
   // return { films, film, progrs, progr, tkts, tkt, $reset, create, getAll, getById, update, remove, getProgrammazione, createProgrammazione, getByPosti };
//});

