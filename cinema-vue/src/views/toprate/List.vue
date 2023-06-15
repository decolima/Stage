<script setup>
import { useRouter, RouterLink } from "vue-router";
import { useTopRateStore, useAuthStore, useAlertStore } from "@/stores";
import { storeToRefs } from "pinia";
import { ref } from "vue";

const store = useTopRateStore();
const authStore = useAuthStore();
const alertStore = useAlertStore();

const { topRates } = storeToRefs(store);
const started = ref(false);

store.getAll();

function getImgUrl(im) {
  return "https://image.tmdb.org/t/p/w500/" + im;
}
</script>

<template>
  <div class="top-rate-list">
  <p class="title has-text-centered">TOP RATED NEL MONDO</p>
  <div class="list">
    <template v-if="!alertStore.isLoading || started">
      <div class="card" v-for="item in topRates.results">
        <div class="card-content">
          <div class="media">
          <div class="media-left">
            <figure class="image is-128x128">
              <img :src="getImgUrl(item.poster_path)" v-bind:alt="pic" />
            </figure>
          </div>
          <div class="media-content">
            <div class="list-item-title">{{ item.title }}</div>
            <div class="list-item-description">
              <p class="is-size-8">{{ item.overview }}</p>
              <p class="is-size-8">Rate: {{ item.vote_average }}</p>
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
