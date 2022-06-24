import FullButton from '../components/full-button.js';
import VesselCard from '../components/vessel-card.js';
import PlannerSchedule from '../components/planner-schedule.js';

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
  
    <planner-schedule terminalId="1" view="${PlannerSchedule.VIEW.daily}">
    </planner-schedule>

    <unscheduled-vessels>

    </unscheduled-vessels>
    `;
  }
}


customElements.define('vessel-planner', VesselPlanner);
