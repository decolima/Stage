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

export function dateFormatterForPost(inputDate){
  try {
    // Split the input date string into day, month, and year
    const parts = inputDate.split('/');
    
    // Ensure there are three parts (day, month, year)
    if (parts.length !== 3) {
      throw new Error('Invalid date format. Please use DD/MM/YYYY.');
    }
    
    const day = parts[0];
    const month = parts[1];
    const year = parts[2];

    // Create a new Date object in the desired format
       // Create a new Date object for the current local time
       const currentLocalTime = new Date();

       // Format the date as YYYY-MM-DDTHH:MM:SS in the local time zone
       const formattedDate = `${year}-${(month).toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}T${currentLocalTime.getHours().toString().padStart(2, '0')}:${currentLocalTime.getMinutes().toString().padStart(2, '0')}:${currentLocalTime.getSeconds().toString().padStart(2, '0')}`;
      console.log(formattedDate)
    return formattedDate;
  } catch (error) {
    return error.message;
  }
}



  
  
  
  
  
  