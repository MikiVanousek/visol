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
        <vessel-card
            name="DHL Transport"
            view="${VesselCard.VIEW.unscheduled}"
        ></vessel-card> 
        <vessel-card
            name="MSC Diana"
            view="${VesselCard.VIEW.unscheduled}"
        ></vessel-card> 
        <vessel-card
            name="Ever Golden"
            view="${VesselCard.VIEW.unscheduled}"
        ></vessel-card> 

        <vessel-card
            name="COSCO Shipping Taurus"
            view="${VesselCard.VIEW.unscheduled}"
        ></vessel-card> 

        <vessel-card
            name="CMA CGM Antoine De Saint Exupery"
            view="${VesselCard.VIEW.unscheduled}"
        ></vessel-card> 
       
        <create-modal></create-modal>
        <update-modal></update-modal>
    </unscheduled-vessels>
    `;
  }
}


customElements.define('vessel-planner', VesselPlanner);
