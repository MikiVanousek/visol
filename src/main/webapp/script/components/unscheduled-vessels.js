class UnscheduledVessels extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    let children = this.innerHTML
    this.innerHTML = `
       <div class="container-md">
        <div class="d-flex flex-row rounded mb-3 p-4 border">
          <div class="d-flex flex-column justify-content-md-start w-25">
            <h5>Unscheduled vessels</h5>
            <form class="form-group me-2 has-icon-beginning" role="search">
              <span class="fa fa-search form-control-feedback"></span>
              <input aria-label="Search" class="form-control" placeholder="Search vessel" type="text">
            </form>
          </div>
        
        <div class="scrollable-container d-flex flex-row justify-content-start w-75">
          ${children}
        </div>
       </div>
       </div>
      `
  }
}

customElements.define('unscheduled-vessels', UnscheduledVessels);
