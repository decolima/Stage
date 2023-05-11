<script setup>
import { useRoute, useRouter, RouterLink } from 'vue-router';
import { ref } from 'vue';
import {
    useSaleStore
    , useAuthStore, useAlertStore
} from '@/stores';
import { storeToRefs } from 'pinia';

const MIN_DATE = new Date().toISOString().slice(0, 10)

const store = useSaleStore();
const alertStore = useAlertStore();

const route = useRoute();
const router = useRouter();

const id = route.params.id;
console.log('id: ', id);

let title = 'Aggiungi Sala';

const { sala } = storeToRefs(store);
const started = ref(false);

store.$reset();

if (id) {
    title = 'Modifica Sale';
    store.getById(id);
}

function onSave() {
    started.value = true;
    (id ? store.update(id) : store.create())
        .then(_ => {
            alertStore.success(id ? 'Sala aggiornato con successo.' : 'Sala creato con successo.');
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
                <label class="label">Nome</label>
                <div class="control is-expanded">
                    <input v-model="sala.nome" class="input" type="text" placeholder="nome" required>
                </div>
            </div>
            <div class="field ">
                <label class="label">Posti</label>
                <div class="control is-expanded">
                    <input v-model="sala.posti" class="input" type="text" placeholder="posti">
                </div>
            </div>
            <div class="field ">
                <label class="label">Righe</label>
                <div class="control is-expanded">
                    <input v-model="sala.post_x" class="input" type="text" placeholder="Posti in riga">
                </div>
            </div>
            <div class="field ">
                <label class="label">Colonne</label>
                <div class="control is-expanded">
                    <input v-model="sala.post_y" class="input" type="number" placeholder="Posti in colona">
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
                    <RouterLink to="/sale/" class="button is-link is-light">Elenco</RouterLink>
                </p>
            </div>
        </form>
    </template>
    <template v-if="alertStore.isLoading && !started">
        <div class="container loader"></div>
    </template>
</template>