<script setup>
import { useRoute, useRouter, RouterLink } from "vue-router";
import { ref, onMounted } from "vue";
import {
  useUsersStore,
  useAuthStore,
  useProgrammazioneStore,
  useTKTStore,
} from "@/stores";
import { useAlertStore } from "@/stores/alert";
import { storeToRefs } from "pinia";
import { formatarData } from "../../helpers/dataUtils";
import Seat from "./Seat.vue";



const MIN_DATE = new Date().toISOString().slice(0, 10);

const store = useProgrammazioneStore();
const tktstore = useTKTStore();
const usersStore = useUsersStore();
const authStore = useAuthStore();
const alertStore = useAlertStore();

const { programmazione } = storeToRefs(store);
const { occupati } = storeToRefs(tktstore);

const route = useRoute();
const router = useRouter();

const id = route.params.id;
console.log("id: ", id);

const started = ref(false);



let unavailableSeats = ref([]);

async function loadData() {
  try {
    // chiama getByProgrammazioneId e attendi
    await tktstore.getByProgrammazioneId(id);
    const postOcupati = tktstore.occupati;
    // Dati ricevuti
    console.log('Dati ricevuti');
    for (let i = 0; i < postOcupati.length; i++) {
      const objeto = postOcupati[i];
      const postiX = objeto.pos_x;
      const postiY = objeto.pos_y;
      unavailableSeats.value.push({ row: postiX, col: postiY });
    }
    console.log("Posti occupati:", unavailableSeats);

    // Il resto del codice qui ...
  } catch (error) {
    console.error(error);
  }
}

// Chiama la funzione loadData per avviare il processo
onMounted(loadData);


store.getById(id).then(response => { null });
store.$reset();
tktstore.$reset();

const posti = ref([]);

function handleSeatSelection(row, col) {
  console.log(`Posto selezionato: row=${row}, col=${col}`);
  tktstore.post_x = row;
  tktstore.post_y = col;
}

const tipi = Object.values(tktstore.Tipo);

function onSave() {
  console.log("posti:", posti.value);
  started.value = true;
  if (posti.value.length > 0) {
    const seatsSelected = posti.value.join(", ");
    alertStore.success(
      `Hai confermato la prenotazione dei seguenti posti: ${seatsSelected}.`
    );

    const Newtkt = {
      programmazione: {
        id: id,
        version: 0,
        prezzo: 10,
      },
      utente: {
        id: authStore.loggedId,
        version: 0,
      },
      tipo: tktstore.tipo_biglietto,
      pos_x: tktstore.post_x,
      pos_y: tktstore.post_y,
      nome_cliente: tktstore.nome_cliente,
      importo: tktstore.importo,
    };

    tktstore
      .create(Newtkt)
      .then((_) => {
        alertStore.success(
          Newtkt
            ? "Biglietto acquistato con successo."
            : "Biglietto creato con successo."
        );
      })
      .catch((error) => {
        alertStore.error("Si è verificato un errore durante il salvataggio.");
      });
  } else {
    alertStore.error(
      "Seleziona almeno un posto per confermare la prenotazione."
    );
    return;
  }
}

</script>


<template>
  <template v-if="!alertStore.isLoading || started">
    <p class="title has-text-centered" v-if="store">
      Acquistare i biglietti per
      <span class="has-text-info">
        {{ formatarData(store.progr.data_programmazione) }}</span>
      , in <span class="has-text-info">{{ store?.progr?.sala?.nome }}</span>
    </p>
    <form method="post" ref="form">
      <div class="column">
        <div class="card">
          <div class="card-content">
            <div class="columns">
              <div class="column is-one-third">
                <div class="card-image">
                  <figure class="image is-256x256">
                    <img src="https://image.tmdb.org/t/p/w500//A1H2lnpur1IofI0ufcImcAnSytP.jpg" alt="Poster di Film" />
                  </figure>
                </div>
              </div>
              <div class="column">
                <div class="field">
                  <label class="label">Titolo</label>
                  <div class="control">
                    <p>{{ store?.progr?.film?.titolo }}</p>
                  </div>
                </div>
                <div class="field">
                  <label class="label">Descrizione</label>
                  <div class="control">
                    <p>{{ store?.progr?.film?.descrizione }}</p>
                  </div>
                </div>
                <div class="field">
                  <label class="label">Regista</label>
                  <div class="control">
                    <p>{{ store?.progr?.film?.regista }}</p>
                  </div>
                </div>
                <div class="field">
                  <label class="label">Età Minima</label>
                  <div class="control">
                    <p>{{ store?.progr?.film?.eta_minima }}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="column">
        <div class="card">
          <div class="card-content">
            <div>
              <p class="has-text-info is-size-5">
                Scegli i posti nella sala {{ store?.progr?.sala?.nome }}
              </p>
              <Seat :selectedSeats="posti" :posti_x="store.progr.sala.posti_x" :posti_y="store.progr.sala.posti_y"
                :unavailableSeats="unavailableSeats" @onSelect="handleSeatSelection" />
            </div>
          </div>
        </div>
      </div>

      <div class="field">
        <label class="label">Importo</label>
        <div class="control">
          <input v-model="tktstore.importo" class="input" type="number" placeholder="importo" required />
        </div>
      </div>

      <div v-if="tipi.length > 0" class="field">
        <label class="label">Scegli il tipo di biglietto</label>
        <div class="select">
          <select v-model="tktstore.tipo_biglietto" required>
            <option v-for="tipo in tipi" :key="tipo">{{ tipo }}</option>
          </select>
        </div>
      </div>

      <div class="field">
        <label class="label">Inserire nome per acquistare il biglietto</label>
        <div class="control">
          <input v-model="tktstore.nome_cliente" class="input" type="text" placeholder="nome_cliente" required />
        </div>
      </div>

      <div class="field is-grouped">
        <p class="control">
          <button @click.prevent="onSave" class="button is-primary" :class="{ 'is-loading': alertStore.isLoading }">
            Compra
          </button>
        </p>
        <p class="control">
          <RouterLink to="/programmazione/pub" class="button is-link is-light">Elenco Programmazioni
          </RouterLink>
        </p>
      </div>
    </form>
  </template>
  <template v-if="alertStore.isLoading && !started">
    <div class="container loader"></div>
  </template>
</template>