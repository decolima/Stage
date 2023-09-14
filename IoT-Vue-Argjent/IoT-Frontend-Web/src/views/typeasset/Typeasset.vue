<script lang="ts" setup>
import { ref } from "vue";
import { useTypeAssetStore } from '../../stores';
import { loadFromServer } from "../../stores";
import { storeToRefs } from "pinia";
import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';

import type { Header, Item } from "vue3-easy-data-table";

const store = useTypeAssetStore();
const { typeassets } = storeToRefs(store);

const headers: Header[] = [
  { text: "TYPE", value: "type"},
  { text: "MODEL", value: "model"},
  { text: "BRAND", value: "brand"},
  { text: "IDENTIFIER_NAME", value: "identifier_name"}
];


const loading = ref(false);
console.log(typeassets.value)

loadFromServer(store, loading);


const itemsSelected = ref<Item[]>([]);
</script>


<template>
  <div class="content is-large">
    <h3> TYPEASSET TABLE </h3>
  </div>
    <Vue3EasyDataTable
         :headers="headers"
         :items="typeassets"
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


