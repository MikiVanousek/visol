import VisolApi from '../api.js';

class SelectResource extends HTMLSelectElement {
  resourcePromise;
  displayAttribute;
  parentName = this.getAttribute('name');
  resourcePrefix = this.getAttribute('prefix');

  constructor(displayAttribute, resourcePromise, resourceName) {
    super();
    this.displayAttribute = displayAttribute;
    this.resourcePromise = resourcePromise;
    this.resourceName = resourceName;
    console.log(this.resourcePrefix);
  }

  connectedCallback() {
    this.innerHTML = ``;
    this.resourcePromise.then((resource) => {
      this.innerHTML = this.buildSelect(resource);
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
    super('name', VisolApi.getTerminals(), 'destination');
  }
}

class SelectBerth extends SelectResource {
  constructor() {
    super(undefined, VisolApi.getBerths('1'), 'berth');
  }
}

customElements.define('select-terminal', SelectTerminal, {extends: 'select'});
customElements.define('select-berth', SelectBerth, {extends: 'select'});

export default SelectResource;
