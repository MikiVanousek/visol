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
                    <icon-circle name="arrow-left" view="${IconCircle.VIEW.default}"></icon-circle>
                    <h3 class="planner-header-date">8 February</h3>
                    <icon-circle name="arrow-right" view="${IconCircle.VIEW.default}"></icon-circle>
                </div>
                <div class="planner-header-sub">
                  <div class="planner-header-sub-in">
                    <full-button view="${FullButton.VIEW.secondary}" icon="fire">Optimise</full-button>
                  </div>
                </div>
            </div>
            <div class="planner-schedule view-day">
              <div class="planner-schedule-in">
                <div class="planner-timeline">
                     ${this.buildTimeline()}
                </div>
                <div class="planner-berths">
                  <div class="planner-berths-berth">
                    <div class="planner-berths-berth-header">
                      <div class="planner-berths-berth-header-info">
                          <icon-circle view="${IconCircle.VIEW.berth}" name="anchor"></icon-circle>
                          <div class="planner-berths-berth-header-info-name">Berth 1</div>
                      </div>
                    </div>
                    <div class="planner-berths-berth-ships">
                      
                    </div>
                  </div>
                  <div class="planner-berths-berth">
                    <div class="planner-berths-berth-header">
                      <div class="planner-berths-berth-header-info">
                          <icon-circle view="${IconCircle.VIEW.berth}" name="anchor"></icon-circle>
                          <div class="planner-berths-berth-header-info-name">Berth 2</div>
                      </div>
                    </div>
                    <div class="planner-berths-berth-ships">
                      
                    </div>
                  </div>
                  
                  <div class="planner-berths-berth">
                    <div class="planner-berths-berth-header">
                      <div class="planner-berths-berth-header-info">
                          <icon-circle view="${IconCircle.VIEW.berth}" name="anchor"></icon-circle>
                          <div class="planner-berths-berth-header-info-name">Berth 3</div>
                      </div>
                    </div>
                    <div class="planner-berths-berth-ships">
              
                    </div>
                  </div>
                  
                  <div class="planner-berths-berth">
                    <div class="planner-berths-berth-header">
                      <div class="planner-berths-berth-header-info">
                          <icon-circle view="${IconCircle.VIEW.berth}" name="anchor"></icon-circle>
                          <div class="planner-berths-berth-header-info-name">Berth 4</div>
                      </div>
                    </div>
                    <div class="planner-berths-berth-ships">
              
                    </div>
                  </div>
                  
                  <div class="planner-berths-berth">
                    <div class="planner-berths-berth-header">
                      <div class="planner-berths-berth-header-info">
                          <icon-circle view="${IconCircle.VIEW.berth}" name="anchor"></icon-circle>
                          <div class="planner-berths-berth-header-info-name">Berth 5</div>
                      </div>
                    </div>
                    <div class="planner-berths-berth-ships">
              
                    </div>
                  </div>
                  
                  <div class="planner-berths-berth">
                    <div class="planner-berths-berth-header">
                      <div class="planner-berths-berth-header-info">
                          <icon-circle view="${IconCircle.VIEW.berth}" name="anchor"></icon-circle>
                          <div class="planner-berths-berth-header-info-name">Berth 6</div>
                      </div>
                    </div>
                    <div class="planner-berths-berth-ships">
              
                    </div>
                  </div>
                  
                  <div class="planner-berths-berth">
                    <div class="planner-berths-berth-header">
                      <div class="planner-berths-berth-header-info">
                          <icon-circle view="${IconCircle.VIEW.berth}" name="anchor"></icon-circle>
                          <div class="planner-berths-berth-header-info-name">Berth 7</div>
                      </div>
                    </div>
                    <div class="planner-berths-berth-ships">
              
                    </div>
                  </div>
                  
                  <div class="planner-berths-berth">
                    <div class="planner-berths-berth-header">
                      <div class="planner-berths-berth-header-info">
                          <icon-circle view="${IconCircle.VIEW.berth}" name="anchor"></icon-circle>
                          <div class="planner-berths-berth-header-info-name">Berth 8</div>
                      </div>
                    </div>
                    <div class="planner-berths-berth-ships">
              
                    </div>
                  </div>
                  
                  <div class="planner-berths-berth">
                    <div class="planner-berths-berth-header">
                      <div class="planner-berths-berth-header-info">
                          <icon-circle view="${IconCircle.VIEW.berth}" name="anchor"></icon-circle>
                          <div class="planner-berths-berth-header-info-name">Berth 9</div>
                      </div>
                    </div>
                    <div class="planner-berths-berth-ships">
                          
                    </div>
                  </div>
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

  buildTimeline() {
    let res = ""
    let split = Array.from(Array(48).keys())
    split.forEach((val) => {
      let hour = Math.floor(val / 2)
      let minutes = val % 2 === 0 ? '00' : '30'
      res += `<div class="planner-timeline-divider">
                    <p class="planner-timeline-divider-time">${hour}:${minutes}</p>
                </div>`
    })
    return res;
  }
}

customElements.define('planner-schedule', PlannerSchedule)
