<script setup>
import { useRoute, RouterLink } from 'vue-router';
import { ref } from 'vue';
import { useFilmsStore, useSaleStore, useAuthStore, useAlertStore, useProgrammazioneStore } from '@/stores';
import { storeToRefs } from 'pinia';

const MIN_DATE = new Date().toISOString().slice(0, 10)

const filmStore = useFilmsStore();
const progStore = useProgrammazioneStore();
const saleStore = useSaleStore();

const alertStore = useAlertStore();
const started = ref(false);

const route = useRoute();
const id = route.params.id;

const { film } = storeToRefs(filmStore);
const { sale } = storeToRefs(saleStore);
//const { prog } = storeToRefs(progStore);

saleStore.getAll();
filmStore.getById(id);
//progStore.getById(id);

function onSave() {
    const Newprog = {
        data_programmazione: progStore.data_programmazione,
        data_pubblicazione: progStore.data_pubblicazione,
        prezzo: progStore.prezzo,
        sala_id: saleStore.tutte_sale ? [] : saleStore.sala_id,
        film_id: id
    };
    console.log('Dati della nuova programmazione:', Newprog);
    progStore.create(Newprog)
        .then(_ => window.location.href = '../../../../programmazione')
        //.then(_ => alertStore.success('Film programmato con successo.'))
        .catch(error => alertStore.error('Si è verificato un errore durante il salvataggio.'))
}

/*
<p class="control">
    <RouterLink :to="`/films/programmazione/add/${id}/programmazione`" class="button is-link is-light">Elenco</RouterLink>
 </p>
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
                    <input v-model="progStore.data_programmazione" class="input" type="date"
                        placeholder="data di programmazione" :min="MIN_DATE" required>
                </div>
            </div>
            <div class="field ">
                <label class="label">Prezzo</label>
                <div class="control is-expanded">
                    <input v-model="progStore.prezzo" class="input" type="number" placeholder="prezzo" required>
                </div>
            </div>
            <p class="has-text-info is-size-5">Dove lo vuoi proiettare?</p>
            <div class="field ">
                <label class="checkbox">
                    <input v-model="saleStore.tutte_sale" type="checkbox">
                    Su tutte le sale
                </label>
            </div>
            <div v-if="!saleStore.tutte_sale" class="field ">
                <label class="label">Scegli le sale</label>
                <div class="select is-multiple">
                    <select v-model="saleStore.sala_id" multiple>
                        <option v-for="sala in sale" :value="sala.id">{{ sala.nome }} {{ sala.id }}</option>
                    </select>
                </div>
            </div>
            <div class="field">
                <label class="label">Data Pubblicazione</label>
                <div class="control">
                    <input v-model="progStore.data_pubblicazione" class="input" type="date"
                        placeholder="data di pubblicazione" :min="MIN_DATE" required>
                </div>
            </div>
            <div class="field is-grouped">
                <p class="control">
                    <button @click.prevent="onSave" class="button is-primary"
                        :class="{ 'is-loading': alertStore.isLoading }">
                        Salva
                    </button>
                </p>
            </div>
        </form>
    </template>
    <template v-if="alertStore.isLoading && !started">
        <div class="container loader"></div>
    </template>
</template>