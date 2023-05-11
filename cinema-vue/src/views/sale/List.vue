<script setup>
import { useRouter, RouterLink } from 'vue-router';
import { useSaleStore, useAuthStore, useAlertStore } from '@/stores';
import { storeToRefs } from 'pinia';
import { ref } from 'vue';

const store = useSaleStore();
const authStore = useAuthStore();
const alertStore = useAlertStore();

const { sale } = storeToRefs(store);
const started = ref(false);

store.getAll();

function onElimina(id){
    started.value = true;
    store.remove(id);
}
</script>

<template>
    <p class="title has-text-centered">Elenco di Sale</p>
    <RouterLink to="/sale/add" class="button is-primary">Aggiungi</RouterLink>
    <div class="list">
        <template v-if="!alertStore.isLoading || started">
            <div class="list-item" v-for="item in sale">
                <div class="list-item-content">
                    <div class="list-item-title">{{ item.nome }}</div>
                    <div class="list-item-description">
                        <p class="is-size-8">Posti totale: {{ item.posti }}</p>
                        <p class="is-size-8">Colonne: {{ item.post_x }}</p>
                        <p class="is-size-8">Righe: {{ item.post_y }}</p>
                    </div>
                </div>
                <div class="list-item-controls">
                    <div class="buttons is-right">
                        <RouterLink :to="`/sale/edit/${item.id}`" class="button is-link" >Modifica</RouterLink>
                        <button @click="onElimina(item.id)" class="button is-danger" :class="{ 'is-loading': alertStore.isLoading }">Elimina</button>
                    </div>
                </div>
            </div>
        </template>
        <template v-if="alertStore.isLoading && !started">
            <div class="container loader"></div>
        </template>
    </div>
</template>