<script setup>
import { useRouter, RouterLink } from 'vue-router';
import { useProgrammazioneStore, useAuthStore, useAlertStore, useSaleStore } from '@/stores';
import { storeToRefs } from 'pinia';

const store = useProgrammazioneStore();
const authStore = useAuthStore();
const alertStore = useAlertStore();
const saleStore = useSaleStore();

const { progrs } = storeToRefs(store);
const { sale } = storeToRefs(saleStore);

store.getAll();
</script>

<template>
    <p class="title has-text-centered">Film in Programma</p>
    <div class="list">
        <template v-if="progrs && progrs.length">
            <div class="list-item" v-for="item in progrs">
                <div class="list-item-content">
                     
                    <div class="list-item-description ">
                        <p class="has-text-info is-size-4">{{ item.film.titolo }}</p>
                        <p>di {{ item.film.regista }}</p>
                        <p>Costo {{ item.prezzo }}€</p>
                        <p>Il {{ item.data_programmazione }}, {{ item.sala.nome }} </p>
                        <span class="tag is-info is-light"> disponibilite per l'acquisto dal {{ item.data_pubblicazione }}</span>
                    </div>
                </div>
                <div class="list-item-controls">
                    <div class="buttons is-right">
                        <RouterLink :to="`#/${item.id}`" class="button is-danger" >Remove</RouterLink>
                    </div>  
                </div>
            </div>
        </template>
        <template v-if="alertStore.isLoading">
            <div class="container loader"></div>
        </template>
    </div>
</template>