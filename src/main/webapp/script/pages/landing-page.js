class LandingPage extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    const children = this.innerHTML;
    this.innerHTML = `<nav-bar></nav-bar>

    <div class="d-flex flex-column justify-content-center align-items-center">
      <h1 class="text-center">
        Welcome to the Visol Project!
      </h1>

      <p>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
      </p>

      <div class="d-grid gap-2 col-5">
        <a class="btn btn-primary btn-dark" href="./vessel_planner.html" type="button">Vessel Planner</a>
        <a class="btn btn-primary btn-dark" href="./" type="button">Terminal Manager</a>
        <a class="btn btn-primary btn-dark" href="./port_authority.html" type="button">Port Authority</a>
        <a class="btn btn-primary btn-dark" href="./" type="button">Researcher</a>
      </div>
    </div>`;
  }
}


customElements.define('landing-page', LandingPage);
