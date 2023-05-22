<script setup>
import { useRoute, useRouter, RouterLink } from 'vue-router';
import { ref } from 'vue';
import { storeToRefs } from 'pinia';
//import Seat from './Seat.vue';
import Seat from './Seat.vue';

const MIN_DATE = new Date().toISOString().slice(0, 10)

const store = useFilmsStore();
const alertStore = useAlertStore();
const route = useRoute();
const router = useRouter();
const id = route.params.id;
console.log('id: ', id);

let title = 'Buy Ticket';

const { film } = storeToRefs(store);
const started = ref(false);

store.$reset();

if (id) {
    const movie = store.getById(id);
    title = `Modifica ${movie.title}`;
}

/*
function onSave() {
    started.value = true;
    (id ? store.update(id) : store.create())
        .then(_ => {
            alertStore.success(id ? 'Biglietto acquistato con successo.' : 'Biglietto creato con successo.');
        }).catch(error => {
            alertStore.error('Si è verificato un errore durante il salvataggio.');
        })
}
*/

function onSave() {
    started.value = true;
        // Azione di conferma prenotazione
        alertStore.success(`Hai confermato la prenotazione dei seguenti posti: ${seatsSelected}.`);
        // Altre azioni come il reset dell'array selectedSeats o la visualizzazione di un riepilogo della prenotazione
        (id ? store.update(id) : store.create())
            .then(_ => {
                alertStore.success(id ? 'Biglietto acquistato con successo.' : 'Biglietto creato con successo.');
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

function selectSeat(row, col) {
    const seat = { row, col };
    const index = posti.value.findIndex(s => s.row === row && s.col === col);
    if (index > -1) {
        // Rimuovi il posto selezionato dagli array selectedSeats e seatCodes
    } else {
        // Aggiungi il posto selezionato all'array selectedSeats
    }
}


/*
<div class="control is-expanded">
    <input v-model="film.eta_minima" class="input" type="number" placeholder="eta minima">
</div>

<div class="control is-expanded">
   <input v-model="film.regista" class="input" type="text" placeholder="regista">
</div>

<div class="control is-expanded">
    <input v-model="film.descrizione" class="input" type="text" placeholder="descrizione">
</div>

<div class="control is-expanded">
   <input v-model="film.titolo" class="input" type="text" placeholder="titolo">
</div>

<div>
    <h2>Scegli i posti nella sala:</h2>
    <div class="seat-map">
        <Seat v-for="(seat, index) in seats" :key="index" :seat="seat" @selected="selectSeat"/>
    </div>
</div>

*/

</script>

<template>
    <p class="title has-text-centered">Buy tickets for {{ film.titolo }}</p>
    <template v-if="!alertStore.isLoading || started">
        <form method="post" ref="form">
            <div class="field ">
                <label class="label">Titolo</label>
                <div class="control is-expanded">
                    <p>{{ film.titolo }}</p>
                </div>
            </div>
            <div class="field ">
                <label class="label">Descrizione</label>
                <div class="control is-expanded">
                    <p>{{ film.descrizione }}</p>
                </div>
            </div>
            <div class="field ">
                <label class="label">Regista</label>
                <div class="control is-expanded">
                    <p>{{ film.regista }}</p>
                </div>
            </div>
            <div class="field ">
                <label class="label">Età Minima</label>
                <div class="control is-expanded">
                    <p>{{ film.eta_minima }}</p>
                </div>
            </div>

            <div>
                <h2>Scegli i posti nella sala:</h2>
                <Seat :selectedSeats="posti" @onSelect="selectSeat" />
            </div>

            <div class="field is-grouped">
                <p class="control">
                    <button @click.prevent="onSave" class="button is-primary"
                        :class="{ 'is-loading': alertStore.isLoading }">
                        Compra
                    </button>
                </p>
                <p class="control">
                    <RouterLink to="/programmazione/pub" class="button is-link is-light">Elenco Programmazioni</RouterLink>
                </p>
            </div>
        </form>
    </template>
    <template v-if="alertStore.isLoading && !started">
        <div class="container loader"></div>
    </template>
</template>