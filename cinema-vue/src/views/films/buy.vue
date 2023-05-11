<script setup>
import { useRoute, useRouter, RouterLink } from 'vue-router';
import { ref } from 'vue';
import {
    useFilmsStore
    , useAuthStore, useAlertStore
} from '@/stores';
import { storeToRefs } from 'pinia';
import Seat from './Seat.vue';

const MIN_DATE = new Date().toISOString().slice(0, 10)

const store = useFilmsStore();
const alertStore = useAlertStore();
const route = useRoute();
const router = useRouter();
const id = route.params.id;
console.log('id: ', id);

let title = 'Buy TKT';

const { film } = storeToRefs(store);
const started = ref(false);
const selectedSeats = ref([]);

store.$reset();

if (id) {
    title = 'Modifica film';
    store.getById(id);
}

/*
function onSave() {
    started.value = true;
    (id ? store.update(id) : store.create())
        .then(_ => {
            alertStore.success(id ? 'Film aggiornato con successo.' : 'Film creato con successo.');
        }).catch(error => {
            alertStore.error('Si è verificato un errore durante il salvataggio.');
        })
}
*/

function onSave() {
    started.value = true;

    if (selectedSeats.value.length > 0) {
        const seatsSelected = selectedSeats.join(", "); // Esempio: trasformare l'array di posti selezionati in una stringa separata da virgola
        // Azione di conferma prenotazione
        alertStore.success(`Hai confermato la prenotazione dei seguenti posti: ${seatsSelected}.`);
        // Altre azioni come il reset dell'array selectedSeats o la visualizzazione di un riepilogo della prenotazione
        (id ? store.update(id) : store.create())
            .then(_ => {
                alertStore.success(id ? 'Film aggiornato con successo.' : 'Film creato con successo.');
                // Altre azioni come la visualizzazione di un elenco dei film aggiornati o creati
            }).catch(error => {
                alertStore.error('Si è verificato un errore durante il salvataggio.');
                // Altre azioni come la visualizzazione di una schermata di errore
            });
    } else {
        alertStore.error('Seleziona almeno un posto per confermare la prenotazione.');
        return;
    }
}

function selectSeat(seat) {
    const index = selectedSeats.value.indexOf(seat);
    if (index > -1) {
        // Rimuovi il posto selezionato dagli array selectedSeats e seatCodes
        selectedSeats.value.splice(index, 1);
    } else {
        // Aggiungi il posto selezionato all'array selectedSeats
        selectedSeats.value.push(seat);
    }
}
</script>

<template>
    <p class="title has-text-centered">buy tkts for {{ title }}</p>
    <template v-if="!alertStore.isLoading || started">
        <form method="post" ref="form">
            <div class="field ">
                <label class="label">Titolo</label>
                <div class="control is-expanded">
                    <input v-model="film.titolo" class="input" type="text" placeholder="titolo" required>
                </div>
            </div>
            <div class="field ">
                <label class="label">Descrizione</label>
                <div class="control is-expanded">
                    <input v-model="film.descrizione" class="input" type="text" placeholder="descrizione">
                </div>
            </div>
            <div class="field ">
                <label class="label">Regista</label>
                <div class="control is-expanded">
                    <input v-model="film.regista" class="input" type="text" placeholder="regista">
                </div>
            </div>
            <div class="field ">
                <label class="label">Eta Minima</label>
                <div class="control is-expanded">
                    <input v-model="film.eta_minima" class="input" type="number" placeholder="eta minima">
                </div>
            </div>
            <template>
            <div>
                <h2>Scegli i posti nella sala:</h2>
                <div class="seat-map">
                    <Seat v-for="seat in seats" :key="seat.id" :seat="seat" @selected="selectSeat" />
                </div>
            </div>
        </template>

            <div class="field is-grouped">
                <p class="control">
                    <button @click.prevent="onSave" class="button is-primary"
                        :class="{ 'is-loading': alertStore.isLoading }">
                        Salva
                    </button>
                </p>
                <p class="control">
                    <RouterLink to="/films/" class="button is-link is-light">Elenco</RouterLink>
                </p>
            </div>
        </form>
    </template>
    <template v-if="alertStore.isLoading && !started">
        <div class="container loader"></div>
    </template>
</template>