class PortAuthority extends HTMLElement {
  static terminalDropdownTag = "chooseTerminal";

  constructor() {
    super();
  }

  connectedCallback() {
    this.innerHTML = `

    <nav-bar role="authority">
        <drop-down name="${(PortAuthority.terminalDropdownTag)}" id="${(PortAuthority.terminalDropdownTag)}"></drop-down>
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
    dropDown.active = dropDown.data.banana;
    dropDown.callBack = this.changeTerminal
    dropDown.render();
  }

  changeTerminal(newVal) {
    console.log(newVal)
  }

}


customElements.define("port-authority", PortAuthority)
