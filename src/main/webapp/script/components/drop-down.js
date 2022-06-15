import FullButton from "./full-button.js";

class DropDown extends HTMLElement {
  constructor() {
    super();
    this._data = null
    this._active = null
    this._callBack = null
  }

  get data() {
    return this._data;
  }

  set data(newVal) {
    this._data = newVal;
  }

  get active() {
    return this._active;
  }

  set active(newVal) {
    this._active = newVal;
  }

  get callBack() {
    return this._callBack;
  }

  set callBack(newVal) {
    this._callBack = newVal;
  }

  get dropName() {
    return this.getAttribute("name")
  }

  get dropSize() {
    return this.hasAttribute("size")
      ? this.getAttribute("size") : FullButton.SIZE.medium
  }

  render() {
    if (this.data != null) {
      this.innerHTML = `
      <div class="dropdown">
        <div data-bs-toggle="dropdown" aria-expanded="false" id="${this.dropName}">
          <full-button icon="caret-down" view="${FullButton.VIEW.secondary}" size="${this.dropSize}">
            ${this.active}
          </full-button>
        </div>
        
        <ul aria-labelledby="dropdownMenu" class="dropdown-menu view-${this.dropName}">
          ${this.buildList()}
        </ul>
      </div>    
    `
      this.addEventListeners()
    }
  }

  buildList() {
    let res = ""
    Object.values(this.data).forEach(val => {
      let active = this.active === val ? 'active' : ''
      res += `<li>
                <button class="dropdown-item ${active}" type="button">${val}</button>
              </li>`
    })
    return res;
  }

  addEventListeners() {
    const listItems = document.querySelectorAll(".dropdown-menu.view-" + this.dropName + " .dropdown-item")
    listItems.forEach((el) => {
      el.addEventListener("click", () => {
        this.active = el.innerText
        this.render()
        this.callBack(el.innerText)
      })
    })
  }
}

customElements.define("drop-down", DropDown)
