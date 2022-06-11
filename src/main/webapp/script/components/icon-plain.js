class IconPlain extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    this.innerHTML = `<span class="fa fa-${this.getAttribute("name")}"></span>`;
  }

}

customElements.define("icon-plain", IconPlain)
