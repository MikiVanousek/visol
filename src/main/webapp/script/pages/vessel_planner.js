import FullButton from "../components/full-button.js";

class VesselPlanner extends HTMLElement {

  constructor() {
    super();
  }

  connectedCallback() {
    this.innerHTML = `
    <nav-bar role="planner">
        <ul class="navbar-nav mb-lg-0">
          <div>
            <full-button 
                icon="download" 
                view="${FullButton.VIEW.secondary}" 
                size="${FullButton.SIZE.large}">
                Export
            </full-button>
          </div>
        </ul>
    </nav-bar>
  
    <planner-schedule></planner-schedule>

    <unscheduled-vessels></unscheduled-vessels>`;
  }
}


customElements.define("vessel-planner", VesselPlanner)
