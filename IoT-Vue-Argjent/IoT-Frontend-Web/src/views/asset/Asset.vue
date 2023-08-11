<script lang="ts" setup>
import { ref } from "vue";
import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';

import type { Header, Item } from "vue3-easy-data-table";

const headers: Header[] = [
  { text: "TYPEASSET", value: "typeasset"},
  { text: "NAME", value: "name"},
  { text: "ACTIVATION", value: "activation"},
  { text: "TAG", value: "tag"}
];

const items: Item[] = [
{typeasset: "Mobile", name: "Sedia", activation: "2023-08-10", tag: "00001"},
{typeasset: "Mobile", name: "Scrivania", activation: "2023-07-17", tag: "00002"},
{typeasset: "Elettronica", name: "Monitor", activation: "2023-05-19", tag: "00003"},
{typeasset: "Elettronica", name: "Telefono", activation: "2023-03-25", tag: "00004"}
];
const itemsSelected = ref<Item[]>([]);
</script>


<template>
  <div class="content is-large">
    <h3> ASSET TABLE </h3>
  </div>
    <Vue3EasyDataTable
         :headers="headers"
         :items="items"
         table-class-name="assetTable"
         v-model:items-selected="itemsSelected"
         buttons-pagination
         border-cell
         alternating
    />
</template>

<style>
.assetTable{
  --easy-table-header-item-padding: 10px 10px;
  --easy-table-body-item-padding: 10px 10px;
  --easy-table-rows-per-page-selector-width: 50px;
}
</style>


