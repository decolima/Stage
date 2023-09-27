<script lang="ts" setup>
import { ref } from "vue";
import { useAssetStore } from "../../stores";
import { loadFromServer } from "../../stores";
import { storeToRefs } from "pinia";
import { formatarData } from '../../helpers'
import Form from '../../components/Form.vue'
import Vue3EasyDataTable from 'vue3-easy-data-table';
import 'vue3-easy-data-table/dist/style.css';

import type { Header, Item, SortType } from "vue3-easy-data-table";

const store = useAssetStore();
const { assets } = storeToRefs(store);

const headers: Header[] = [
  { text: "TYPEASSET", value: "typeasset"},
  { text: "NAME", value: "name"},
  { text: "ACTIVATION", value: "activation"},
  { text: "TAG", value: "tag"}
];

const loading = ref(false);
console.log(assets.value)
const items = ref<Item[]>([]);

const loadFromServerAsset = async () => {
      await loadFromServer(store, loading);
      const array: Item[] = assets.value;
      const newArray = array.map(item => {
        return {
          name: item.name,
          activation: formatarData(item.activation),
          tag: item.tag?.address ?? "no tag",
          typeasset: `${item.typeasset?.brand ?? "No Brand"} - ${item.typeasset?.model ?? "No Model"}`
        }       
      })
      
      items.value = newArray;
      loading.value = false;
    };

loadFromServerAsset();
let visible = false;

const showForm = () =>{
  visible = !visible
  console.log(visible)
}
const sortBy ="activation";
const sortType: SortType = "asc";

</script>


<template>
  <div class="content is-large">
    <Form></Form>
    <h3> ASSET TABLE </h3>
  </div>
    <Vue3EasyDataTable
         :headers="headers"
         :items="items"
         :sort-by="sortBy"
         :sort-type="sortType"
         table-class-name="assetTable"
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


