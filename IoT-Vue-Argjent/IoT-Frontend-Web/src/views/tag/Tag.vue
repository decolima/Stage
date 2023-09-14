<script lang="ts" setup>
import { ref } from "vue";
import { useTagStore } from "../../stores";
import { loadFromServer } from "../../stores";
import { storeToRefs } from "pinia";
import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';
import type { Header, Item} from "vue3-easy-data-table";

const store = useTagStore();

const { tags } = storeToRefs(store);

const headers: Header[] = [
  { text: "MAC ADDRESS", value: "address"},
  { text: "NAME", value: "name"},
  { text: "ACTIVATION", value: "activation"},
  { text: "STATUS", value: "status"},
  { text: "STATUS USE", value: "status_use"},
];


const loading = ref(false);
console.log(tags.value)

loadFromServer(store, loading);

const itemsSelected = ref<Item[]>([]);
</script>


<template>
  <div class="content is-large">
    <h3> TAG TABLE </h3>
  </div>
    <Vue3EasyDataTable
         :headers="headers"
         :items="tags"
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


