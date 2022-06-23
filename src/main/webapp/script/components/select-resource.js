import VisolApi from '../api.js';

class SelectResource extends HTMLSelectElement {
  resourcePromise;
  displayAttribute;

  constructor(displayAttribute) {
    super();
    this.displayAttribute = displayAttribute;
  }

  setResourcePromise(resourcePromise) {
    this.innerHTML = ``;
    this.resourcePromise = resourcePromise;
    this.resourcePromise.then((resource) => {
      this.innerHTML = this.buildSelect(resource);
      this.dispatchEvent(new Event('change'));
    }).catch((err) => console.log('Error fetching resource for the selector: ', err));
  }

  buildSelect(resource) {
    return Object.keys(resource).map((i) =>
      `<option value="${i}">${this.displayAttribute === undefined ? i :
        resource[i][this.displayAttribute]}</option>`).join('\n');
  }
}

class SelectTerminal extends SelectResource {
  constructor() {
    super('name', 'destination');
    this.setResourcePromise(VisolApi.getTerminals());
  }
}

class SelectBerth extends SelectResource {
  constructor() {
    super(undefined, 'berth');
  }

  setTerminal(i) {
    this.setResourcePromise(VisolApi.getBerths(i));
  }
}

customElements.define('select-terminal', SelectTerminal, {extends: 'select'});
customElements.define('select-berth', SelectBerth, {extends: 'select'});

export default SelectResource;
