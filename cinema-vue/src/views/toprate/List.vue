<script setup>
import { useRouter, RouterLink } from "vue-router";
import { useTopRateStore, useAuthStore, useAlertStore, useFilmsStore } from "@/stores";
import { storeToRefs } from "pinia";
import { ref } from "vue";

const storeFilm = useFilmsStore();
const store = useTopRateStore();
const authStore = useAuthStore();
const alertStore = useAlertStore();

const { isLogged, isAdmin } = storeToRefs(authStore);

const { topRates } = storeToRefs(store);
const { film } = storeToRefs(storeFilm);

const started = ref(false);

store.getAll();

function getImgUrl(im) {
  return "https://image.tmdb.org/t/p/w500/" + im;
}


function onSave(title,overview,poster_path) {

  
  let NEWfilm = {
    
        titolo: title,
        descrizione: overview,
        cartellone: poster_path,
        regista: "AUTO",
        eta_minima: 0
      
    };

    console.log("list",NEWfilm);
 

  started.value = true;
    (storeFilm.importar(NEWfilm))
        .then(_ => {
            alertStore.success('Film creato con successo.');
        }).catch(error => {
            alertStore.error('Si è verificato un errore durante il salvataggio.');
        })
}
 


</script>

<template>
  <div class="top-rate-list">
  <p class="title has-text-centered">TOP RATED NEL MONDO</p>
  <div class="list">
    <template v-if="!alertStore.isLoading || started">
      <div class="card" v-for="item in topRates.results">
        <div class="card-content" style="min-height: 280px;">
          <div class="media">
          <div class="media-left">
            <figure class="image is-128x128">
              <img :src="getImgUrl(item.poster_path)" v-bind:alt="pic" />
            </figure>
          </div>
          <div class="media-content">
            <div class="list-item-title">{{ item.title }}</div>
            <div class="list-item-description">
              <p class="is-size-6">{{ item.overview }}</p>
              <p class="is-size-8">Rate: {{ item.vote_average }}</p>
            </div>
            <div class="card-item-controls">
              <div class="buttons is-right">
            <button id="{{ item.poster_path }}" v-if="isAdmin" @click.prevent="onSave(item.title,item.overview,item.poster_path)" class="button is-primary"
             :class="{ 'is-loading': alertStore.isLoading }" >Aggiungi
             
             </button>
            </div>
          </div>


          </div>
        </div>
        </div>
      </div>
    </template>
    <template v-if="alertStore.isLoading && !started">
      <div class="container loader"></div>
    </template>
  </div>
</div>
</template>
