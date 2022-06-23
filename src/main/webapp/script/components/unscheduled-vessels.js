import VesselCard from './vessel-card.js';

class UnscheduledVessels extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    const children = this.innerHTML;
    this.innerHTML = `
     <div class="unscheduled-vessels">
          <div class="unscheduled-vessels-body">
            <div class="unscheduled-vessels-headers">
              <h6>Unscheduled vessels</h6>
              <form class="form-group has-icon-beginning" role="search">
                <span class="fa fa-search form-control-icon"></span>
                <input 
                  id="vessels-search-bar"
                  aria-label="Search" 
                  class="form-control" 
                  placeholder="Search vessel" 
                  type="text"
                  />
              </form>
            </div>
          
          <div class="scrollable-container" id="unscheduled-vessels">
              ${children}
              
              <div class="scrollable-container-empty-search">
                 ooops ... No vessel found in unscheduled vessels...
              </div>
              
              <div class="scrollable-container-empty">
                 No unscheduled vessel
              </div>
          </div>
          
         </div>
       </div>
      `;
    this.addEventListeners();
  }

  addEventListeners() {
    const vessels = document.getElementById('unscheduled-vessels').children;
    const input = document.getElementById('vessels-search-bar');

    window.addEventListener('load', () => {
      if (vessels.length === 2) {
        const emptyEl = document
            .getElementsByClassName(
                'scrollable-container-empty')[0];
        emptyEl.style.display = 'flex';
        input.disabled = true;
      }
    });

    input.addEventListener('keyup', () => {
      const search = input.value.toLowerCase();
      let empty = true;

      for (let i = 0; i < vessels.length-2; i++) {
        if (!vessels[i].getAttribute('name')
            .toLowerCase()
            .includes(search)) {
          vessels[i].style.display = 'none';
        } else {
          empty = false;
          vessels[i].style.display = 'flex';
        }
      }
      const emptyEl = document
          .getElementsByClassName(
              'scrollable-container-empty-search')[0];
      emptyEl.style.display = empty ? 'flex' : 'none';
    });
  }
}

customElements.define('unscheduled-vessels', UnscheduledVessels);
