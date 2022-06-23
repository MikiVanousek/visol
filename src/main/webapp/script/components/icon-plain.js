class IconPlain extends HTMLElement {
  static VIEW = {
    default: 'default',
    button: 'button',
    secondary: 'secondary',
  };

  constructor() {
    super();
  }

  connectedCallback() {
    const view = this.hasAttribute('view') ?
      this.getAttribute('view') : IconPlain.VIEW.default;
    this.innerHTML =
      `<span class="fa fa-${this.getAttribute('name')} 
            view-${view}"></span>`;
  }
}

customElements.define('icon-plain', IconPlain);

export default IconPlain;
