<script lang="ts" setup>
import { ref } from "vue";
import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';

import type { Header, Item } from "vue3-easy-data-table";

const headers: Header[] = [
  { text: "NAME", value: "name"},
  { text: "RESPONSIBLE", value: "responsible"},
  { text: "EMAIL", value: "email"},
  { text: "PHONE", value: "phone"}
];

const items: Item[] = [
  { name: "Laser", responsible: "Paolo Conta", email: "paoloconta@laseri.it", phone: "0125789645"},
  { name: "RGI", responsible: "Andrea Porcu", email: "andreaporcu@rgi.it", phone: "0125236754"},
  { name: "Dedagroup", responsible: "Renato Bocchi", email: "renatobocchi@dedagroup.it", phone: "0125316858"},
  { name: "Sorma", responsible: "Marino Di Todaro", email: "marinoditodaro@sorma.it", phone: "0125913465"}
];
</script>


<template>
  <div class="content is-large">
    <h3> COMPANY TABLE </h3>
  </div>
    <Vue3EasyDataTable
         :headers="headers"
         :items="items"
         table-class-name="companyTable"
         buttons-pagination
         border-cell
         alternating
    />
</template>

<style>
.companyTable{
  --easy-table-header-item-padding: 10px 10px;
  --easy-table-body-item-padding: 10px 10px;
}
</style>


