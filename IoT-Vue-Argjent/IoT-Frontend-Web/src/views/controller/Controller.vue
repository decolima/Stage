<script lang="ts" setup>
import { ref } from "vue";
import { useControllerStore } from '../../stores';
import { loadFromServer } from "../../stores";
import { storeToRefs } from "pinia";
import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';

import type { Header} from "vue3-easy-data-table";

const store = useControllerStore();
const { controllers } = storeToRefs(store);

const headers: Header[] = [
  { text: "NAME", value: "name"},
  { text: "ACTIVATION", value: "activation"},
  { text: "STATUS", value: "status"},
  { text: "DISCOVERY", value: "discovery"},
  { text: "ADDRESS", value: "address"},
  { text: "GEOLOCATION", value: "geolocation"},
  { text: "RESPONSIBLE", value: "responsible"}
];

const loading = ref(false);
console.log(controllers.value)



loadFromServer(store, loading);

</script>


<template>
  <div class="content is-large">
    <h3> CONTROLLER TABLE </h3>
  </div>
    <Vue3EasyDataTable
         :headers="headers"
         :items="controllers"
         table-class-name="controllerTable"
         buttons-pagination
         border-cell
         alternating
    />
</template>

<style>
.controllerTable{
  --easy-table-header-item-padding: 10px 10px;
  --easy-table-body-item-padding: 10px 10px;
}
</style>


