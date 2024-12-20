class Navbar extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    const children = this.innerHTML;
    const role = this.hasAttribute('role') ?
        '-' + this.getAttribute('role') : '';
    this.innerHTML = `
    <nav class="navbar navbar-expand-lg sticky-top">
      <div class="container-fluid">
        <a class="navbar-brand" href="./">
          <img 
            src="./assets/logo${role}.svg"
            alt="logo" 
            class="navbar-brand-logo" />
        </a>
        ${children}
      </div>
    </nav>`;
  }
}

customElements.define('nav-bar', Navbar);
