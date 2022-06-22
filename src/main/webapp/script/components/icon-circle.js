class IconCircle extends HTMLElement {
  static VIEW = {
    default: 'default',
    primary: 'primary',
    berth: 'berth',
    closed: 'closed',
    addVessel: 'add-vessel',
  };

  constructor() {
    super();
  }

  connectedCallback() {
    const view =
      this.hasAttribute('view') ?
        this.getAttribute('view') : IconCircle.VIEW.default;
    this.innerHTML = `
       <div class="icon-circle view-${view}">
        <div class="icon-circle-in">
          <icon-plain name="${this.getAttribute('name')}"></icon-plain>
        </div>
       </div>
    `;
  }
}

customElements.define('icon-circle', IconCircle);

export default IconCircle;
