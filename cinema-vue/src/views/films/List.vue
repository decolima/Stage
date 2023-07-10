<script setup>
import { useRouter, RouterLink } from "vue-router";
import { useFilmsStore, useAuthStore, useAlertStore } from "@/stores";
import { storeToRefs } from "pinia";
import { ref } from "vue";

const store = useFilmsStore();
const authStore = useAuthStore();
const alertStore = useAlertStore();

const { films } = storeToRefs(store);
const started = ref(false);

store.getAll();

function onElimina(id) {
  started.value = true;
  store.remove(id);
}

function getImgUrl(im) {
  return "https://image.tmdb.org/t/p/w500/" + im;
}
</script>

<template>
  <p class="title has-text-centered">Elenco Film</p>
  <RouterLink to="/films/add" class="button is-primary">Aggiungi</RouterLink>
  <div class="list">
    <template v-if="!alertStore.isLoading || started">
      <div class="card" v-for="item in films">
            <div class="card-content" style="min-height: 300 px;">
                <div class="media">
                    <div class="media-left">
                    <figure class="image is-128x128">
                        <img :src="getImgUrl(item.cartellone)" v-bind:alt="pic" />
                    </figure>
                    </div>
                    <div class="media-content">
                        <div class="list-item-title">{{ item.titolo }}</div>
                        <div class="list-item-description">
                            <p class="is-size-6">{{ item.descrizione }}</p>
                            <p>di {{ item.regista }}</p>
                            <p>Eta minima {{ item.eta_minima }}</p>
                        </div>
                    </div>
                </div>

                <div class="card-item-controls">
          <div class="buttons is-right">
            <RouterLink :to="`/films/edit/${item.id}`" class="button is-link"
              >Modifica</RouterLink
            >
            <RouterLink
              :to="`/films/programmazione/${item.id}`"
              class="button is-link"
              >Programmazione</RouterLink
            >
            <button
              @click="onElimina(item.id)"
              class="button is-danger"
              :class="{ 'is-loading': alertStore.isLoading }"
            >
              Elimina
            </button>
          </div>
        </div>

            </div>

      </div>
    </template>
    <template v-if="alertStore.isLoading && !started">
      <div class="container loader"></div>
    </template>
  </div>
</template>
