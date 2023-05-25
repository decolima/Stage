<script setup>
import { useRoute, useRouter, RouterLink } from 'vue-router';
import { ref } from 'vue';
import { useFilmsStore, useAuthStore, useAlertStore, useSaleStore,useProgrammazioneStore } from '@/stores';
import { storeToRefs } from 'pinia';
import Seat from './Seat.vue';
import { formatarData } from '../../helpers/dataUtils';



const MIN_DATE = new Date().toISOString().slice(0, 10)

const store = useProgrammazioneStore();
const alertStore = useAlertStore();

const route = useRoute();//Viene ottenuta l'istanza della rotta corrente (route) e del router (router) 
const router = useRouter(); //tramite le funzioni useRoute e useRouter rispettivamente

const id = route.params.id; //Viene ottenuto l'id del film dalla rotta corrente (id) 
console.log('id: ', id); //e viene eseguito il log su console del suo valore

const { programa } = storeToRefs(store); //La variabile film viene assegnata all'oggetto film memorizzato nell'istanza di store
const started = ref(false); //Viene creato un valore booleano reattivo started inizialmente impostato su false


store.$reset(); //Viene chiamato il metodo $reset dell'istanza di store, che reimposta lo stato dello store ai valori iniziali

if (id) {
  store.getById(id)
    .then(() => {
      console.log(JSON.stringify(store));
      // Outras ações com os resultados atualizados da chamada assíncrona
    })
    .catch(error => {
      console.error(error);
      // Lógica para lidar com erros durante a chamada assíncrona
    });
}

let title = 'Buy Ticket';





function onSave() {
    started.value = true;
    if (posti.value.length > 0) {
        const seatsSelected = posti.value.join(", "); // Esempio: trasformare l'array di posti selezionati in una stringa separata da virgola
        // Azione di conferma prenotazione
        alertStore.success(`Hai confermato la prenotazione dei seguenti posti: ${seatsSelected}.`);
        // Altre azioni come il reset dell'array selectedSeats o la visualizzazione di un riepilogo della prenotazione
        (id ? store.update(id)
 : store.create())
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

 



</script>

<template>
    
    <template v-if="!alertStore.isLoading || started">

        <p class="title has-text-centered"  v-if="store">Acquistare i biglietti per <span class="has-text-info">{{ formatarData(store.progr.data_programmazione) }}</span>, in <span class="has-text-info">{{ store.progr.sala.nome }}</span></p>
        

        <form method="post" ref="form">

<div class="column">
  <div class="card">
    <div class="card-content">
      <div class="columns">
        <div class="column is-one-third">
          <div class="card-image">
            <figure class="image is-256x256" >
                <img src="https://image.tmdb.org/t/p/w500//A1H2lnpur1IofI0ufcImcAnSytP.jpg" alt="Poster do Filme" >
            </figure>
          </div>
        </div>
        <div class="column">
          
  
            <div class="field">
          <label class="label">Titolo</label>
          <div class="control">
            <p>{{ store.progr.film.titolo }}</p>
          </div>
        </div>
        <div class="field">
          <label class="label">Descrizione</label>
          <div class="control">
            <p>{{ store.progr.film.descrizione }}</p>
          </div>
        </div>
        <div class="field">
          <label class="label">Regista</label>
          <div class="control">
            <p>{{ store.progr.film.regista }}</p>
          </div>
        </div>
        <div class="field">
          <label class="label">Età Minima</label>
          <div class="control">
            <p>{{ store.progr.film.eta_minima }}</p>
          </div>      
         
        
        </div>


      </div>
    </div>
 
</div>
</div></div>
<div class="column">
<div class="card">
    <div class="card-content">

            <div>
                <p class="has-text-info is-size-5">Scegli i posti nella sala  {{ store.progr.sala.nome }}</p>
              
                <Seat :selectedSeats="posti" :posti_x="store.progr.sala.posti_x" :posti_y="store.progr.sala.posti_y" @onSelect="selectSeat" />

            </div>
        </div>         </div> </div>
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