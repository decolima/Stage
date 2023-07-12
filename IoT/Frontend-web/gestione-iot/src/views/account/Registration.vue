<script setup>
import { ref } from 'vue';
import { storeToRefs } from 'pinia'
import { useUsersStore } from '@/stores'
import { useAlertStore } from '@/stores';

//contiene l'oggetto form
const form = ref(null);

//destrutturo lo store
const store = useUsersStore();
const alertStore = useAlertStore();

const firstName = ref('');
const lastName = ref('');
const email = ref('');
const pwd = ref('');

const onRegistration = (e) => {
    if (form.value.checkValidity() === false) {
        form.value.reportValidity();
        return;
    }
    let ruolo = 'User';
    if (pwd.value === 'Admin') {
        ruolo = 'Admin';
    }
    store.registration({ firstName: firstName.value, lastName: lastName.value, email: email.value, pwd: pwd.value, ruolo })
        .then(json => {
            alertStore.success('Grazie per esserti registrato.');
        })
        .catch(error => {
            alertStore.error('Si è verificato un errore durante la registrazione.');
        })
}
</script>

<template>
    <p class="title has-text-centered">Registrazione</p>
    <form method="post" ref="form">
        <div class="field ">
            <label class="label">First Name</label>
            <div class="control is-expanded">
                <input v-model="firstName" class="input" type="text" placeholder="firstName" required>
            </div>
        </div>
        <div class="field">
            <label class="label">Last Name</label>
            <div class="control">
                <input v-model="lastName" class="input" type="date" placeholder="lastName" required>
            </div>
        </div>
        <div class="field">
            <label class="label">Email</label>
            <div class="control">
                <input v-model="email" class="input" type="date" placeholder="email" required>
            </div>
        </div>
        <div class="field">
            <label class="label">Password</label>
            <div class="control is-expanded">
                <input v-model="pwd" class="input" type="password" placeholder="password" required>
            </div>
        </div>
        <div class="field is-grouped">
            <p class="control">
                <button @click.prevent="onRegistration" class="button is-primary"
                    :class="{ 'is-loading': alertStore.isLoading }">
                    Salva
                </button>
            </p>
            <p class="control">
                <RouterLink to="login" class="button is-link is-light">Login</RouterLink>
            </p>
        </div>
    </form>
</template>