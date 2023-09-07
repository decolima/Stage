<script lang="ts" setup>

import { ref } from 'vue';
import { useAssetMonitoringStore } from "../../stores";
import { loadFromServer } from "../../stores";
import { storeToRefs } from "pinia";
import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';

import type { Header, ClickRowArgument } from "vue3-easy-data-table";

const store = useAssetMonitoringStore();
const { assetsMonitoring } = storeToRefs(store);

const headers: Header[] = [
  { text: "CONTROLLER", value: "controller"},
  { text: "TAG MAC ADDRESS", value: "tag_macaddress"},
  { text: "TAG BATTERY %", value: "tag_use"},
  { text: "ASSET NAME", value: "asset_name"},
  { text: "ASSET ACTIVATION", value: "asset_activation"},
  { text: "STATUS", value: "discovery_status"},
  { text: "RESPONSIBLE", value: "responsible"},
  { text: "COMPANY_NAME", value: "company_name"},
  { text: "TAG_ACTIVATION", value: "tag_activation"},
  { text: "DISCOVERY_DATE", value: "discovery_date"},
  { text: "GEOLOCATION", value: "geolocation"}
];

const loading = ref(false);
console.log(assetsMonitoring.value)

loadFromServer(store, loading);

const goToController = (item: ClickRowArgument) => {
  console.log(JSON.stringify(item));
};
</script>


<template>
  <div class="content is-large">
    <h3> ASSET MONITORING TABLE </h3>
   
  </div>
    <Vue3EasyDataTable
         :headers="headers"
         :items="assetsMonitoring"
         table-class-name="assetMonitoringTable"
         border-cell
         alternating
         :loading="loading"
         @click-row="goToController"
    />
    <div id="row-clicked"></div>

    
</template>

<style>
.assetMonitoringTable{
  --easy-table-header-item-padding: 10px 10px;
  --easy-table-body-item-padding: 10px 10px;
}

</style>

