<script lang="ts" setup>
import { ref } from "vue";
import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';

import type { Header, Item } from "vue3-easy-data-table";

const headers: Header[] = [
  { text: "TYPE", value: "type"},
  { text: "MODEL", value: "model"},
  { text: "BRAND", value: "brand"},
  { text: "IDENTIFIER_NAME", value: "identifier_name"}
];

const items: Item[] = [
    {type: "Mobile", model: "A10240", brand: "SKLUM", identifier_name: "Sedia"},
    {type: "Elettronica", model: "T56311", brand: "MOTOROLA", identifier_name: "Telefono"},
    {type: "Elettronica", model: "SX85412", brand: "SAMSUNG", identifier_name: "Monitor"},
    {type: "Mobile", model: "MN38794", brand: "IKEA", identifier_name: "Scrivania"}
];
const itemsSelected = ref<Item[]>([]);
</script>


<template>
  <div class="content is-large">
    <h3> TYPEASSET TABLE </h3>
  </div>
    <Vue3EasyDataTable
         :headers="headers"
         :items="items"
         table-class-name="typeAssetTable"
         v-model:items-selected="itemsSelected"
         buttons-pagination
         border-cell
         alternating
    />
</template>

<style>
.typeAssetTable{
  --easy-table-header-item-padding: 10px 10px;
  --easy-table-body-item-padding: 10px 10px;
}
</style>


