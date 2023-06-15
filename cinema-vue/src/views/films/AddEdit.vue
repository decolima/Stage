<script setup>
import { useRoute, useRouter, RouterLink } from 'vue-router'; //sono fornite da Vue Router e consentono di accedere alla rotta corrente, 
import { ref } from 'vue'; //è una funzione di supporto di Vue che consente di creare un valore reattivo                       //al router e di utilizzare il componente <router-link> rispettivamente
import { useFilmsStore, useAuthStore, useAlertStore} from '@/stores';
import { storeToRefs } from 'pinia';

const MIN_DATE = new Date().toISOString().slice(0, 10) //Vengono definiti alcuni valori costanti come MIN_DATE, che viene impostato sull'oggi come stringa in formato ISO senza l'ora

const store = useFilmsStore();
const alertStore = useAlertStore();

const route = useRoute();//Viene ottenuta l'istanza della rotta corrente (route) e del router (router) 
const router = useRouter(); //tramite le funzioni useRoute e useRouter rispettivamente

const id = route.params.id; //Viene ottenuto l'id del film dalla rotta corrente (id) 
console.log('id: ', id); //e viene eseguito il log su console del suo valore

let title = 'Aggiungi film'; // viene inizializzato come "Aggiungi film"

const { film } = storeToRefs(store); //La variabile film viene assegnata all'oggetto film memorizzato nell'istanza di store
const started = ref(false); //Viene creato un valore booleano reattivo started inizialmente impostato su false

store.$reset(); //Viene chiamato il metodo $reset dell'istanza di store, che reimposta lo stato dello store ai valori iniziali

if (id) { //Il valore di title viene aggiornato a "Modifica film" se un id è presente nella rotta corrente
    title = 'Modifica film'; 
    store.getById(id); //Se l'id del film è presente nella rotta corrente, viene richiamato il metodo getById
}                      //che recupera le informazioni del film dal server e le salva nello store

function onSave() {
    started.value = true;
    (id ? store.update(id) : store.create())
        .then(_ => {
            alertStore.success(id ? 'Film aggiornato con successo.' : 'Film creato con successo.');
        }).catch(error => {
            alertStore.error('Si è verificato un errore durante il salvataggio.');
        })
}
</script>

<template>
    <p class="title has-text-centered">{{ title }}</p>
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
                    <input v-model="film.descrizione" class="input" type="text" placeholder="descrizione" required>
                </div>
            </div>
            <div class="field ">
                <label class="label">Regista</label>
                <div class="control is-expanded">
                    <input v-model="film.regista" class="input" type="text" placeholder="regista" required>
                </div>
            </div>
            <div class="field ">
                <label class="label">Eta Minima</label>
                <div class="control is-expanded">
                    <input v-model="film.eta_minima" class="input" type="number" placeholder="eta minima" required>
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
                    <RouterLink to="/films/" class="button is-link is-light">Elenco</RouterLink>
                </p>
            </div>
        </form>
    </template>
    <template v-if="alertStore.isLoading && !started">
        <div class="container loader"></div>
    </template>
</template>