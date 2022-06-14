import IconCircle from "../components/icon-circle.js";
import FullButton from "../components/full-button.js";

class PortAuthority extends HTMLElement {

  constructor() {
    super();
  }

  connectedCallback() {
    const children = this.innerHTML
    this.innerHTML = `

    <nav-bar>
        <div class="dropdown">
      
        <div data-bs-toggle="dropdown" aria-expanded="false" id="dropdownMenu">
          <full-button icon="caret-down" view="${FullButton.VIEW.secondary}">
            Car terminal
          </full-button>
        </div>
        
        <ul aria-labelledby="dropdownMenu" class="dropdown-menu" >
          <li>
            <button class="dropdown-item active" type="button">Car Terminal</button>
          </li>
          <li>
            <button class="dropdown-item" type="button">Fish Terminal</button>
          </li>
          <li>
            <button class="dropdown-item" type="button">Banana Terminal</button>
          </li>
          <li>
            <button class="dropdown-item" type="button">Trees Terminal</button>
          </li>
        </ul>
        
      </div>
    </nav-bar>

    <icon-cirle name="ship" view="${IconCircle.VIEW.default}"></icon-cirle>

    <icon-cirle name="cloud-moon" view="${IconCircle.VIEW.closed}"></icon-cirle>

    <icon-cirle name="anchor" view="${IconCircle.VIEW.berth}"></icon-cirle>

    <icon-cirle name="arrow-right" view="${IconCircle.VIEW.default}"></icon-cirle>

    <full-button view="${FullButton.VIEW.primary}">Save</full-button>

    <planner-schedule></planner-schedule>

    <unscheduled-vessels></unscheduled-vessels>`;
  }
}


customElements.define("port-authority", PortAuthority)
