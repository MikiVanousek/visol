import VisolApi from "../api.js"

class SelectResource extends HTMLElement {
  resourcePromise
  displayAttribute
  name

  constructor(displayAttribute, resourcePromise, name) {
    super()
    this.displayAttribute = displayAttribute
    this.resourcePromise = resourcePromise
    this.name = name
  }

  connectedCallback() {
    this.innerHTML = ``
    this.resourcePromise.then(resource =>{
      this.innerHTML = this.buildSelect(resource)
    }).catch(err => console.log("Error fetching resource for the selector: ", err))
  }

  buildSelect(resource) {
    return `
    <label class="form-label me-3" for="${name}-select-terminal"><b>${this.name.charAt(0).toUpperCase() + this.name.slice(1)}:</b></label>
    <select class="form-select form-select-sm" id="${name}-select-terminal" name="vessel-destination"> 
      ${Object.keys(resource).map(i => `<option value="${i}">${resource[i][this.displayAttribute]}</option>`).join('\n')}
    </select>
    `
  }
}

class SelectTerminal extends SelectResource {
  constructor() {
    super('name', VisolApi.getTerminals(), 'terminals')
  }
}

customElements.define('select-terminal', SelectTerminal)

export default SelectResource;
