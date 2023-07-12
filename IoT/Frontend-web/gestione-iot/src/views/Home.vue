<script setup>
import { storeToRefs } from 'pinia';

import { useAuthStore } from '@/stores';

const authStore = useAuthStore();
const { isLogged, loggedUser } = storeToRefs(authStore);


</script>

<template>
    <div class="columns">
        <div class="column is-half">
            <div v-if="isLogged">
                <h1 class="title">Benvenuto {{ loggedUser }}!!</h1>
                <p class="is-size-4">You're logged with Vue 3 + Pinia & JWT!!</p>
            </div>
        </div>
    </div>
</template>