import FullButton from "../components/full-button.js";

class PortAuthority extends HTMLElement {
  static terminalDropdownTag = "chooseTerminal";

  constructor() {
    super();
  }

  connectedCallback() {
    this.innerHTML = `

    <nav-bar role="authority">
        <drop-down 
            name="${(PortAuthority.terminalDropdownTag)}" 
            id="${(PortAuthority.terminalDropdownTag)}"
            size="${FullButton.SIZE.large}">
        </drop-down>
        
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

    this.addData()
  }

  addData() {
    const dropDown = document.getElementById(PortAuthority.terminalDropdownTag);
    dropDown.data = {
      car: "Car Terminal",
      fish: "Fish Terminal",
      banana: "Banana Terminal",
      trees: "Trees Terminal"
    };
    dropDown.active = dropDown.data.fish;
    dropDown.callBack = this.changeTerminal
    dropDown.render();
  }

  changeTerminal(newVal) {
    console.log(newVal)
  }

}


customElements.define("port-authority", PortAuthority)
