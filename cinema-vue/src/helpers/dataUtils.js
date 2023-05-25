
// Função para transformar a data
export function formatarData(data) {
    // Verifica se a data está no formato AAAA-MM-DD
    if (/^\d{4}-\d{2}-\d{2}$/.test(data)) {
      // Divide a data em ano, mês e dia
      const [ano, mes, dia] = data.split('-');
  
      // Retorna a data no formato DD/MM/AAAA
      return `${dia}/${mes}/${ano}`;
    }
  
    // Retorna a data original se não estiver no formato esperado
    return data;
  }