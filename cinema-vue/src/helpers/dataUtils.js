
// Funzione per transformare la data
export function formatarData(data) {
    // Verifica se la data è nel formato AAAA-MM-DD
    if (/^\d{4}-\d{2}-\d{2}$/.test(data)) {
      // Divide la data in anno, mese e giorno
      const [ano, mes, dia] = data.split('-');
      // Ritorna la data nel formato DD/MM/AAAA
      return `${dia}/${mes}/${ano}`;
    }
    // Ritorna la data originale se non è nel formato previsto
    return data;
  }