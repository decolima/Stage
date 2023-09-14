export async function loadFromServer(store, loading){
    loading.value = true;
    await store.getAll();
    loading.value = false;
}