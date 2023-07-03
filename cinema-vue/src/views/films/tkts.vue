<script setup>
import { useRouter, RouterLink } from 'vue-router';
import { useTKTStore, useAuthStore, useAlertStore } from '@/stores';
import { storeToRefs } from 'pinia';
import { ref, onMounted } from 'vue';
import { formatarData } from "../../helpers/dataUtils";
import VueQrcode from 'vue-qrcode';



const store = useTKTStore();
const authStore = useAuthStore();
const alertStore = useAlertStore();

const { tkts } = storeToRefs(store);
const started = ref(false);

store.getAll();

console.log(store);

function onElimina(id){
    started.value = true;
    store.remove(id);
}

</script>


<template>
    <p class="title has-text-centered">Miei biglietti</p>
    <div class="list">
        <template v-if="!alertStore.isLoading || started">
            <div class="card" v-for="item in tkts">
                <div class="card-content ">
                  <div class="media">
                  <div class="media-left">
                  <VueQrcode value="{{ item.id }}  " :size="20" />
                </div>
                <div class="media-content">
                    <div class="list-item-title is-size-4">{{ item.programmazione.film.titolo }} </div>
                   

                    


                    <div class="list-item-description">
                        <p>{{ formatarData(item.programmazione.data_programmazione) }} </p>
                        <p>{{ item.programmazione.sala.nome }} </p> 

                        <p>Riga: {{ item.pos_x }}, Colonna: {{ item.pos_y }} </p> 
                        <p>#{{ item.id }} </p> 
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
</template>