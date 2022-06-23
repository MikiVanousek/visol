import VisolApi from '../api.js';

class SelectResource extends HTMLElement {
  resourcePromise;
  displayAttribute;
  parentName = this.getAttribute('name');
  resourcePrefix = this.getAttribute('prefix');

  constructor(displayAttribute, resourcePromise, resourceName) {
    super();
    this.displayAttribute = displayAttribute;
    this.resourcePromise = resourcePromise;
    this.resourceName = resourceName;
  }

  connectedCallback() {
    this.innerHTML = ``;
    this.resourcePromise.then((resource) => {
      this.innerHTML = this.buildSelect(resource);
    }).catch((err) => console.log('Error fetching resource for the selector: ', err));
  }

  buildSelect(resource) {
    return `<label class="form-label me-3" for="${this.parentName}-form-${this.resourceName}">
  <b>${this.resourceName.charAt(0).toUpperCase() + this.resourceName.slice(1)}:</b>
</label>
<select class="form-select form-select-sm" id="${this.parentName}-form-${this.resourceName}"
  name="${this.resourcePrefix}-${this.resourceName}">
  ${Object.keys(resource).map((i) =>
    `<option value="${i}">${this.displayAttribute === undefined ? i :
        resource[i][this.displayAttribute]}</option>`).join('\n')}
</select>
    `;
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

customElements.define('select-terminal', SelectTerminal);
customElements.define('select-berth', SelectBerth);

export default SelectResource;
