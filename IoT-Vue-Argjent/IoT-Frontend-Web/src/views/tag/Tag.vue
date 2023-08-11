<script lang="ts" setup>
import { ref } from "vue";
import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';

import type { Header, Item } from "vue3-easy-data-table";

const headers: Header[] = [
  { text: "MAC ADDRESS", value: "address"},
  { text: "NAME", value: "name"},
  { text: "ACTIVATION", value: "activation"},
  { text: "STATUS", value: "status"},
  { text: "STATUS USE", value: "status_use"},
];

const items: Item[] = [
  {address: "00000ABB28FC", name: "00001", activation: "2023-08-11", status: 0, status_use: "TBD"},
  {address: "00000CTT66QT", name: "00002", activation: "2023-05-17", status: 0, status_use: "TBD"},
  {address: "00000SRP17OL", name: "00003", activation: "2023-07-19", status: 0, status_use: "TBD"},
  {address: "00000KMG54SM", name: "00004", activation: "2023-04-07", status: 0, status_use: "TBD"}
];
const itemsSelected = ref<Item[]>([]);
</script>


<template>
  <div class="content is-large">
    <h3> TAG TABLE </h3>
  </div>
    <Vue3EasyDataTable
         :headers="headers"
         :items="items"
         table-class-name="tagTable"
         v-model:items-selected="itemsSelected"
         buttons-pagination
         border-cell
         alternating
    />
</template>

<style>
.tagTable{
  --easy-table-header-item-padding: 10px 10px;
  --easy-table-body-item-padding: 10px 10px;
}
</style>


