<script setup>
import { ref } from "vue";
import { RouterLink} from "vue-router";
import 'bulma/css/bulma.css'
import { useAuthStore } from "@/stores";
import { storeToRefs } from "pinia";

const authStore = useAuthStore();
const { isLogged } = storeToRefs(authStore);

const burger = ref(null);
const nav = ref(null);

const onBurgerClick = () => {
  burger.value.classList.toggle("is-active");
  nav.value.classList.toggle("is-active");
};

const onLogout = () => {
  authStore.logout();
};
</script>

<template>
    <nav v-show="isLogged" class="navbar" role="navigation" aria-label="main navigation">
        <div class="navbar-brand">
          <RouterLink to="/company"><a class="navbar-item" href="">
            <img src="https://cdn.worldvectorlogo.com/logos/tag-logo.svg" >
          </a>
        </RouterLink>
          <a ref="burger" @click.prevent="onBurgerClick" role="button" class="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
            <span aria-hidden="true"></span>
          </a>
        </div>
      
        <div ref="nav" id="navbarBasicExample" class="navbar-menu">
          <div class="navbar-start">
            <RouterLink to="/controller" class="navbar-item">
                  Controllers
            </RouterLink>
            <RouterLink to="/monitoring" class="navbar-item">
                  Monitoring
            </RouterLink>
            <RouterLink to="/asset" class="navbar-item">
                  Assets
            </RouterLink>
            <RouterLink to="/typeasset" class="navbar-item">
                  Type Assets
            </RouterLink>
            <RouterLink to="/tag" class="navbar-item">
                  Tags
            </RouterLink>
          </div>
      
          <div class="navbar-end">
            <div class="navbar-item">
              <div class="buttons">
                <RouterLink to="/account/registration" class="button is-primary">
                      <strong>Sign up</strong>
                </RouterLink>
                <a class="button is-light" @click.prevent="onLogout"> Log out </a>
              </div>
            </div>
          </div>
        </div>
      </nav>
</template>

