import FullButton from "./full-button.js";

class PlannerSchedule extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {
    this.innerHTML = `
    <div class="container-md">
        <div class="planner">
            <div class="planner-header">
                <full-button view="${FullButton.VIEW.secondary}">Today</full-button>
            </div>
        </div>  
    </div>`
  }
}

customElements.define('planner-schedule', PlannerSchedule)
