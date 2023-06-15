<script setup>
import { useRouter, RouterLink } from 'vue-router';
import { useProgrammazioneStore, useAuthStore, useAlertStore, useSaleStore } from '@/stores';
import { storeToRefs } from 'pinia';
import { formatarData } from '../../helpers/dataUtils';

const store = useProgrammazioneStore();
const authStore = useAuthStore();
const alertStore = useAlertStore();
const saleStore = useSaleStore();

const { progrs } = storeToRefs(store);
const { sale } = storeToRefs(saleStore);

store.getAllPub();

function formatDate(date = new Date()) {
    const year = date.toLocaleString('default', { year: 'numeric' });
    const month = date.toLocaleString('default', { month: '2-digit' });
    const day = date.toLocaleString('default', { day: '2-digit' });

    return [year, month, day].join('-');
}

const daysToExpire = 14; // Numero di giorni massimo che una programmazione può durare

// Rimuovi le programmazioni scadute
progrs.value = progrs.value.filter((item) => {
    const expireDate = new Date(item.data_programmazione).getTime() + (daysToExpire * 24 * 60 * 60 * 1000); // Calcola la data di scadenza
    return expireDate >= Date.now(); // Restituisci solo le programmazioni che non sono ancora scadute
});

</script>

<template>
    <p class="title has-text-centered">Film in Programma</p>
    <div class="list">
        <template v-if="progrs && progrs.length">
            <div class="list-item in-programma" v-for="item in progrs">
                <div class="list-item-content">
                    <div class="list-item-description">
                        <p class="has-text-info is-size-4">{{ item.film.titolo }}
                            <span class="tag is-success"
                                v-if="formatDate(new Date()) == item.data_programmazione">OGGI</span>
                            <span class="tag is-warning" v-if="formatDate(new Date()) < item.data_programmazione">in
                                arrivo</span>
                        </p>
                        <p>di {{ item.film.regista }}</p>
                        <p>Costo {{ item.prezzo }}€</p>
                        <p>Il {{ formatarData(item.data_programmazione) }}, {{ item.sala.nome }}</p>
                    </div>
                </div>
                <div class="list-item-controls">
                    <div class="buttons is-right">
                        <RouterLink :to="`/films/biglietti/${item.id}`" class="button is-link">Buy Ticket</RouterLink>
                    </div>
                </div>
            </div>
        </template>
        <template v-if="alertStore.isLoading">
            <div class="container loader"></div>
        </template>
    </div>
</template>