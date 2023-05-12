<script setup>
import { useRoute, RouterLink } from 'vue-router';
import { ref } from 'vue';
import { useFilmsStore, useSaleStore, useAuthStore, useAlertStore, useProgrammazioneStore } from '@/stores';
import { storeToRefs } from 'pinia';

const MIN_DATE = new Date().toISOString().slice(0, 10)

const filmeStore = useFilmsStore();
const progStore = useProgrammazioneStore();

const alertStore = useAlertStore();
const saleStore = useSaleStore();
const started = ref(false);

const route = useRoute();
const id = route.params.id;

const { film } = storeToRefs(filmeStore);
const { sale } = storeToRefs(saleStore);
const { prog } = storeToRefs(progStore);

saleStore.getSale(id);
filmeStore.getById(id);
progStore.getById(id);
/*
function onSave() {
    prog.createProgrammazione()
        .then(_ => alertStore.success('Film programmato con successo.'))
        .catch(error => alertStore.error('Si è verificato un errore durante il salvataggio.'))
}
*/
function onSave() {
  const newProg = {
    il: progStore.il,
    prezzo: progStore.prezzo,
    sale_id: saleStore.tutte_sale ? [] : saleStore.sale_id,
    film_id: id
  };
  console.log('Dati della nuova programmazione:', newProg);
  prog.createProgrammazione(newProg)
    .then(_ => alertStore.success('Film programmato con successo.'))
    .catch(error => alertStore.error('Si è verificato un errore durante il salvataggio.'))
}
/*
function onSave() {
    started.value = true;
    prog.createProgrammazione()
        .then(_ => {
            alertStore.success('Film programmato con successo.');
        }).catch(error => {
            alertStore.error('Si è verificato un errore durante il salvataggio.');
        })
}
*/
</script>

<template>
    <p class="title has-text-centered is-size-4">Programma
        <span class="has-text-info is-size-3">{{ film.titolo }}</span>
    </p>
    <template v-if="!alertStore.isLoading || started">
        <form method="post" ref="form">
            <div class="field">
                <label class="label">Quando</label>
                <div class="control">
                    <input v-model="progStore.il" class="input" type="date" placeholder="data di programmazione"
                        :min="MIN_DATE" required>
                </div>
            </div>
            <div class="field ">
                <label class="label">Prezzo</label>
                <div class="control is-expanded">
                    <input v-model="progStore.prezzo" class="input" type="number" placeholder="prezzo" required>
                </div>
            </div>

            <p class="has-text-info is-size-2">Dove lo vuoi proiettare?</p>

            <div class="field ">
                <label class="checkbox">
                    <input v-model="saleStore.tutte_sale" type="checkbox">
                    Su tutte le sale
                </label>
            </div>

            <div v-if="!saleStore.tutte_sale" class="field ">
                <label class="label">Scegli le sale</label>
                <div class="select is-multiple">
                    <select v-model="saleStore.sale_id" multiple>
                        <option v-for="sala in sale" :value="sala.id">{{ sala.nome }}</option>
                    </select>
                </div>
            </div>

            <div class="field is-grouped">
                <p class="control">
                    <button @click.prevent="onSave" class="button is-primary"
                        :class="{ 'is-loading': alertStore.isLoading }">
                        Salva
                    </button>
                </p>
                <p class="control">
                    <RouterLink :to="`/films/${id}/programmazione`" class="button is-link is-light">Elenco</RouterLink>
                </p>
            </div>
        </form>
    </template>
    <template v-if="alertStore.isLoading && !started">
        <div class="container loader"></div>
    </template>
</template>