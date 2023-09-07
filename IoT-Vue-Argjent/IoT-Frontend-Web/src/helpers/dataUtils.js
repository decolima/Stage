export function formatarData(data) {
  const dateObj = new Date(data);

  if (isNaN(dateObj.getTime())) {
    // Restituisci null se la data non è valida
    return null;
  }

  const dia = String(dateObj.getDate()).padStart(2, '0');
  const mes = String(dateObj.getMonth() + 1).padStart(2, '0');
  const ano = dateObj.getFullYear();
  const ore = String(dateObj.getHours()).padStart(2, '0');
  const minuti = String(dateObj.getMinutes()).padStart(2, '0');

  return `${dia}/${mes}/${ano} ${ore}:${minuti}`;
}