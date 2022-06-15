class IconCircle extends HTMLElement {
  static VIEW = {
    default: "default",
    primary: "primary",
    berth: "berth",
    closed: "closed"
  }

  constructor() {
    super();
  }

  connectedCallback() {
    this.innerHTML = `
       <div class="icon-circle view-${this.getAttribute("view")}">
        <icon-plain name="${this.getAttribute("name")}"></icon-plain>
       </div>`;
  }

}

customElements.define("icon-cirle", IconCircle)

export default IconCircle;
