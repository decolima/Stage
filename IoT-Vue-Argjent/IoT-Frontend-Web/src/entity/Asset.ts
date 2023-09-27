import text from "./map/Text";

export default class Asset {
    typeasset = new text("");
    name = new text("");
    activation = new text("");
    controller = new text("");
    tag = new text("")


createJSONFromAsset(Asset) {
    const json = {
      "typeasset": { "id": parseInt(Asset.typeasset.value) },
      "name": Asset.name.value,
      "activation": Asset.activation.value,
      "controller": { "id": parseInt(Asset.controller.value) },
      "tag": { "id": parseInt(Asset.tag.value) }
    };
  
    return json;
  }

}
