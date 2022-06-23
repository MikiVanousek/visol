import FullButton from '../components/full-button.js';
import VesselCard from '../components/vessel-card.js';
import VisolApi from '../api.js';

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
  
    <planner-schedule terminalId="1">
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
       
        <vessel-modal name="create"></vessel-modal>
        <vessel-modal name="update" id="update-m"></vessel-modal>

    </unscheduled-vessels>
    `;
    VisolApi.getVessel('18').then((res) => {
      const vessel = res;
      console.log(vessel);
      document.getElementById('update-m').setVessel(vessel);
    },
    );
  }
}


customElements.define('vessel-planner', VesselPlanner);
