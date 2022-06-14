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
          <div>ViSOL</div>
          ${this.hasAttribute('role') ? `<div class="navbar-brand-role">${this.getAttribute('role')}</div>` : ''}
        </a>
        
        ${children}

        <ul class="navbar-nav mb-lg-0">
          <div>
            <full-button icon="download" view="secondary">Export</full-button>
          </div>
        </ul>
        
      </div>
    </nav>`


  }
}

customElements.define('nav-bar', Navbar);
