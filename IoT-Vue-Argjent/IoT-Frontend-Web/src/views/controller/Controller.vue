<script lang="ts" setup>
import { ref } from "vue";
import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';

import type { Header, Item } from "vue3-easy-data-table";

const headers: Header[] = [
  { text: "NAME", value: "name"},
  { text: "ACTIVATION", value: "activation"},
  { text: "STATUS", value: "status"},
  { text: "DISCOVERY", value: "discovery"},
  { text: "ADDRESS", value: "address"},
  { text: "GEOLOCATION", value: "geolocation"},
  { text: "RESPONSIBLE", value: "responsible"}
];

const items: Item[] = [
  { name: "Sede ausiliare", activation: "2023-08-10", status: "Active", discovery: 'Disable', address: "Via Ivrea 11", geolocation: "Strambino", responsible: "Stefania Romagnani"},
  { name: "Sede principale", activation: "2023-02-13", status: "Disable", discovery: 'Disable', address: "Via Pont 23", geolocation: "Aosta", responsible: "Andree Nunes De Lima"},
  { name: "Sede ausiliare", activation: "2022-07-25", status: "Active", discovery: 'Active', address: "Via Domenici 15", geolocation: "Milano", responsible: "Marco La Mura"},
  { name: "Sede ausiliare", activation: "2022-12-05", status: "Canceled", discovery: 'Disable', address: "Via delle Campane 10", geolocation: "Torino", responsible: "Alessandro Agostini"},
];
</script>


<template>
  <div class="content is-large">
    <h3> CONTROLLER TABLE </h3>
  </div>
    <Vue3EasyDataTable
         :headers="headers"
         :items="items"
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


