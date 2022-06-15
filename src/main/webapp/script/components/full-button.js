class FullButton extends HTMLElement {
  static VIEW = {
    primary: "primary",
    secondary: "secondary",
    disabled: "disabled"
  }

  constructor() {
    super();
  }

  connectedCallback() {
    const children = this.innerHTML
    const icon = this.hasAttribute("icon") ? `<icon-plain name="${this.getAttribute("icon")}"></icon-plain>` : ``
    this.innerHTML = `
        <button 
            class="full-button view-${this.getAttribute("view")}" 
            type="button">${children} ${icon}</button>
    `;
  }
}

customElements.define("full-button", FullButton)

export default FullButton;
