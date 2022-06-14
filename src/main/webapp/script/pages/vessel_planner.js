class VesselPlanner extends HTMLElement {

  constructor() {
    super();
  }

  connectedCallback() {
    this.innerHTML = `
    <nav-bar></nav-bar>
  
    <planner-schedule></planner-schedule>

    <unscheduled-vessels></unscheduled-vessels>`;
  }
}


customElements.define("vessel-planner", VesselPlanner)
