class Navbar extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    let children = this.innerHTML;
    this.innerHTML = `
    <nav class="navbar navbar-expand-lg sticky-top">
      <div class="container-fluid">
        <a class="navbar-brand" href="./index.html">
          <img src="./assets/logo${this.hasAttribute('role') ? '-' + this.getAttribute('role') : ''}.svg" alt="logo" class="navbar-brand-logo">
        </a>
         
        ${children}
        
      </div>
    </nav>`
  }
}

customElements.define('nav-bar', Navbar);
