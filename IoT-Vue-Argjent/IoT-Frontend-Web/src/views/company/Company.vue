<script lang="ts" setup>
import { ref } from 'vue';
import { useCompanyStore } from "../../stores";
import { loadFromServer } from "../../stores";
import { storeToRefs } from "pinia";
import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';

import type { Header, ClickRowArgument } from "vue3-easy-data-table";

const store = useCompanyStore();
const { companies } = storeToRefs(store);

const headers: Header[] = [
  { text: "NAME", value: "name"},
  { text: "RESPONSIBLE", value: "responsible"},
  { text: "EMAIL", value: "email"},
  { text: "PHONE", value: "phone"}
];

const loading = ref(false);
console.log(companies.value)


loadFromServer(store, loading);

const goToController = (item: ClickRowArgument) => {
  console.log(JSON.stringify(item));
};
</script>


<template>
  <div class="content is-large">
    <h3> COMPANY TABLE </h3>
  </div>
    <Vue3EasyDataTable
         :headers="headers"
         :items="companies"
         table-class-name="companyTable"
         buttons-pagination
         border-cell
         alternating
         :loading="loading"
         @click-row="goToController"
    />
    <div id="row-clicked"></div>

    
</template>

<style>
.companyTable{
  --easy-table-header-item-padding: 10px 10px;
  --easy-table-body-item-padding: 10px 10px;
}
</style>


