class IconPlain extends HTMLElement {
  static VIEW = {
    default: "default",
    button: "button"
  }

  constructor() {
    super();
  }

  connectedCallback() {
    this.innerHTML = `<span class="fa fa-${this.getAttribute("name")}"></span>`;
  }

}

customElements.define("icon-plain", IconPlain)
