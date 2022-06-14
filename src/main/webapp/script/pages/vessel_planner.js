class VesselPlanner extends HTMLElement {

  constructor() {
    super();
  }

  connectedCallback() {
    this.innerHTML = `
    <nav-bar role="planner"></nav-bar>
  
    <planner-schedule></planner-schedule>

    <unscheduled-vessels></unscheduled-vessels>`;
  }
}


customElements.define("vessel-planner", VesselPlanner)
