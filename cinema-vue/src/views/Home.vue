<script setup>
import { storeToRefs } from 'pinia';

import { useAuthStore } from '@/stores';

const authStore = useAuthStore();
const { isLogged, loggedUser } = storeToRefs(authStore);


</script>

<template>

<div class="columns">
  <div class="column is-half">    <div v-if="isLogged">
        <h1 class="title">Benvenuto al Cinema Reativo {{ loggedUser }}!!</h1>
        <p class="is-size-4">You're logged in Cinema App with Vue 3 + Pinia & JWT!!</p>
    </div></div>
  <div class="column">
    <figure class="image is-16by9">
  <img   src="https://media.timeout.com/images/105771947/1024/768/image.jpg">
</figure></div>

</div>



</template>