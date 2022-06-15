import FullButton from "./full-button.js";
import IconCircle from "./icon-circle.js";

class PlannerSchedule extends HTMLElement {
  static viewDropdownTag = "chooseView";

  constructor() {
    super();
  }

  connectedCallback() {
    this.innerHTML = `
    <div class="container-md">
        <div class="planner">
            <div class="planner-header">
                <div class="planner-header-sub">
                  <div class="planner-header-sub-in">
                    <full-button view="${FullButton.VIEW.secondary}">Today</full-button>
                    <drop-down name="${(PlannerSchedule.viewDropdownTag)}" id="${(PlannerSchedule.viewDropdownTag)}"></drop-down>
                  </div>
                </div>
                <div class="planner-header-sub">
                    <icon-cirle name="arrow-left" view="${IconCircle.VIEW.default}"></icon-cirle>
                    <icon-cirle name="arrow-right" view="${IconCircle.VIEW.default}"></icon-cirle>
                </div>
                <div class="planner-header-sub">
                  <div class="planner-header-sub-in">
                    <full-button view="${FullButton.VIEW.secondary}" icon="fire">Optimise</full-button>
                  </div>
                </div>
            </div>
        </div>  
    </div>`

    this.addData()
  }

  addData() {
    const dropDown = document.getElementById(PlannerSchedule.viewDropdownTag);
    dropDown.data = {
      daily: "Daily",
      weekly: "Week",
    };
    dropDown.active = dropDown.data.daily;
    dropDown.callBack = this.changeView
    dropDown.render();
  }

  changeView(newVal) {
    console.log(newVal)
  }
}

customElements.define('planner-schedule', PlannerSchedule)
