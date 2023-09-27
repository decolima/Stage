export default class UtilityLabels{

    UtilityLabels(){};

    static mappa =  {
        type : "Tipo",
        model : "Modello",
        brand : "Brand",
        identifierName : "Nome Identificativo",
        typeasset: "ID Tipo Asset",
        name: "Nome",
        tag: "ID Tag",
        controller: "ID Controller",
        activation: "Data Attivazione"
    };

    static traduci(value){
            return this.mappa[value];
     }
}
