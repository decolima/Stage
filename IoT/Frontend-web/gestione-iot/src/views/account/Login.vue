<script setup>
import { ref } from 'vue';
import { storeToRefs } from 'pinia'
import { useAuthStore } from '@/stores'
import { useRouter } from 'vue-router';
import { RouterLink } from 'vue-router'
import { useAlertStore } from '@/stores';

let email;
let pwd;

//contiene l'oggetto form
const form = ref(null);

//router
const router = useRouter();

//authStore
const store = useAuthStore();;
const alertStore = useAlertStore();

const onLogin = (e) => {
    if (form.value.checkValidity() === false) {
        form.value.reportValidity();
        return;
    }
    store.login({ email, pwd })
        .then(_ => router.push('/'))
        .catch(error => {
            console.log(error);
            alertStore.error('Autenticazione fallita!');
        })
}
</script>

<template>
    <p class="title has-text-centered">Login</p>
    <form method="post" ref="form">
        <div class="field">
            <label class="label">Email</label>
            <div class="control">
                <input v-model="email" class="input" type="text" placeholder="email" required>
            </div>
        </div>
        <div class="field">
            <label class="label">Password</label>
            <div class="control">
                <input v-model="pwd" class="input" type="password" placeholder="password" required>
            </div>
        </div>
        <div class="field is-grouped">
            <p class="control">
                <button @click.prevent="onLogin" class="button is-primary" :class="{ 'is-loading': alertStore.isLoading }">
                    Login
                </button>
            </p>
            <p class="control">
                <RouterLink to="registration" class="button is-link is-light">Registrati</RouterLink>
            </p>
        </div>
    </form>


</template>