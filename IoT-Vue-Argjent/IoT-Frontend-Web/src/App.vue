
<template>
    <section>
        <header>
            <Navbar></Navbar>
        </header>
        <div>
            <RouterView />
        </div>
    </section>
</template>

<script setup>
import Navbar from './components/Navbar.vue';
import { RouterView } from 'vue-router'
</script>
