
<template>
    <header>
        <Navbar></Navbar>
    </header>
    <div>
        <Login></Login>
    </div>
    <div>
        <RouterView />
    </div>
</template>

<script setup>
import Navbar from './components/Navbar.vue';
import Login from './views/account/Login.vue';
import { RouterView } from 'vue-router'
</script>
