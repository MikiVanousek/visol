import FullButton from './full-button.js';
import IconCircle from './icon-circle.js';
import VesselCard from './vessel-card.js';
import BerthClosed from './berth-closed.js';
import CurrentTime from './current-time.js';
import VisolApi from '../api.js';

class PlannerSchedule extends HTMLElement {
  static viewDropdownTag = 'chooseView';

  constructor() {
    super();
  }

  get terminalId() {
    return this._terminalId;
  }

  set terminalId(newVal) {
    this._terminalId = newVal;
  }

  get vessels() {
    return this._vessels;
  }

  set vessels(newVal) {
    this._vessels = newVal;
  }

  async connectedCallback() {
    this.terminalId =
        this.hasAttribute('teminalId') ?
            parseInt(this.getAttribute('terminalId')) : 1;
    this.innerHTML = `
    <div class="planner">
        <div class="planner-header">
            <div class="planner-header-sub">
              <div class="planner-header-sub-in">
                <full-button 
                    view="${FullButton.VIEW.secondary}">
                    Today
                </full-button>
                <drop-down
                    name="${(PlannerSchedule.viewDropdownTag)}" 
                    id="${(PlannerSchedule.viewDropdownTag)}">
                </drop-down>
              </div>
            </div>
            <div class="planner-header-sub">
                <icon-circle 
                    name="arrow-left" 
                    view="${IconCircle.VIEW.default}"></icon-circle>
                <h3 class="planner-header-date">8 February</h3>
                <icon-circle 
                    name="arrow-right" 
                    view="${IconCircle.VIEW.default}"></icon-circle>
            </div>
            <div class="planner-header-sub">
              <div class="planner-header-sub-in">
                <full-button 
                    view="${FullButton.VIEW.secondary}" 
                    icon="fire">Optimise</full-button>
              </div>
            </div>
        </div>
        <div class="planner-schedule view-day">
          <div class="planner-schedule-actions">
              <icon-circle name="undo"></icon-circle>
              <icon-circle name="redo"></icon-circle>
              <icon-circle
                  view="${IconCircle.VIEW.addVessel}" 
                  name="ship"
                  data-bs-toggle="modal" 
                  data-bs-target="#update-modal">
              </icon-circle>
          </div>
          <div class="planner-schedule-in">
            <!-- TODO only add the current time if the view contains the current date. remove it if it no longer does -->
            <current-time view="${CurrentTime.VIEW.daily}"></current-time>
            <div class="planner-timeline">
               ${this.buildTimeline()}
            </div>
            <div class="planner-berths" id="planner-berth-${this.terminalId}">
              <div class="planner-berths-dividers">
                  ${this.buildDividers()}
              </div>
              
              <div class="planner-berths-berth">
                <div class="planner-berths-berth-header">
                  <div class="planner-berths-berth-header-info">
                      <icon-circle 
                        view="${IconCircle.VIEW.berth}" 
                        name="anchor"></icon-circle>
                      <div class="planner-berths-berth-header-info-name">
                        Berth 2</div>
                  </div>
                </div>
                <berth-closed
                    view="${BerthClosed.VIEW.daily}"
                    open="08:00:00"
                    close="19:00:00">
                </berth-closed>
                <div class="planner-berths-berth-ships">
                  <vessel-card
                        name="Queen Mary 2"
                        view="${VesselCard.VIEW.infeasible}"
                        overflow="0"
                        arrival="0:30"
                        departure="2:10"
                    ></vessel-card>
                    <vessel-card
                        name="Britania"
                        view="${VesselCard.VIEW.infeasible}"
                        overflow="1"
                        arrival="0:30"
                        departure="2:10"
                    ></vessel-card>
                    <vessel-card
                        name="Britania"
                        view="${VesselCard.VIEW.infeasible}"
                        overflow="2"
                        arrival="0:45"
                        departure="2:30"
                    ></vessel-card>
                    <vessel-card
                        name="Britania"
                        view="${VesselCard.VIEW.infeasible}"
                        overflow="3"
                        arrival="0:55"
                        departure="1:40"
                    ></vessel-card>
                    <vessel-card
                        name="Britania"
                        view="${VesselCard.VIEW.infeasible}"
                        overflow="4"
                        arrival="1:10"
                        departure="1:60"
                    ></vessel-card>
                </div>
              </div>
              
              <div class="planner-berths-berth">
                <div class="planner-berths-berth-header">
                  <div class="planner-berths-berth-header-info">
                      <icon-circle 
                        view="${IconCircle.VIEW.berth}" 
                        name="anchor"></icon-circle>
                      <div class="planner-berths-berth-header-info-name">
                        Berth 2</div>
                  </div>
                </div>
                <berth-closed
                    view="${BerthClosed.VIEW.daily}"
                    open="08:00:00"
                    close="01:00:00">
                </berth-closed>
                <div class="planner-berths-berth-ships">
                  <vessel-card
                        name="Queen Mary 2"
                        view="${VesselCard.VIEW.automatic}"
                        arrival="0:30"
                        departure="3:10"
                    ></vessel-card>
                    <vessel-card
                        name="Britania"
                        view="${VesselCard.VIEW.manual}"
                        arrival="3:15"
                        departure="4:10"
                    ></vessel-card>
                </div>
              </div>
            
            <div class="planner-berths-berth">
              <div class="planner-berths-berth-header">
                <div class="planner-berths-berth-header-info">
                    <div class="planner-berths-berth-header-info-left">
                      <icon-circle 
                        view="${IconCircle.VIEW.berth}" 
                        name="anchor"></icon-circle>
                      <div class="planner-berths-berth-header-info-name">
                        Berth 2</div>
                    </div>
                    <div class="planner-berths-berth-header-info-details">
                      <p class="planner-berths-berth-header-info-details-item">
                        30<span>x</span>20<span>x</span>100</p>
                      <p class="planner-berths-berth-header-info-details-item">
                        125 con/h</p>
                    </div>
                </div>
              </div>
                <berth-closed
                    view="${BerthClosed.VIEW.daily}"
                    open="08:00:00"
                    close="00:00:00">
                </berth-closed>
              <div class="planner-berths-berth-ships">

              </div>
            </div>
           
              <div class="planner-berths-berth">
                <div class="planner-berths-berth-header">
                  <div class="planner-berths-berth-header-info">
                      <icon-circle 
                        view="${IconCircle.VIEW.berth}" 
                        name="anchor"></icon-circle>
                      <div class="planner-berths-berth-header-info-name">
                        Berth 2</div>
                  </div>
                </div>
                <div class="planner-berths-berth-ships">
  
                </div>
              </div>
            
          </div>
        </div>
    </div>`;

    this.addData();
    window.onload = function() {
      document.querySelector('.planner-current-wrap').scrollToMiddle();
    };
    await this.loadBerths();
    await this.loadVessels();
    await this.loadSchedules(null, null);
  }

  addData() {
    const dropDown = document.getElementById(PlannerSchedule.viewDropdownTag);
    dropDown.data = {
      daily: 'Daily',
      weekly: 'Week',
    };
    dropDown.active = dropDown.data.daily;
    dropDown.callBack = this.changeView;
    dropDown.render();
  }

  changeView(newVal) {
    console.log(newVal);
  }

  async loadBerths() {
    const schedule =
        document.getElementById(`planner-berth-${this.terminalId}`);
    const berths = await VisolApi.getBerthsPerTerminal(this.terminalId);
    Object.entries(berths).forEach(([id, berth]) => {
      const berthEl = document.createElement('planner-berth');
      berthEl.id = id;
      berthEl.data = berth;
      schedule.appendChild(berthEl);
    });
  }

  async loadVessels() {
    this.vessels =
        await VisolApi.getVesselsPerTerminal(this.terminalId);
  }

  async loadSchedules(timeFrom, timeTo) {
    const berthsSchedules =
        await VisolApi.getSchedulesPerTerminal(this.terminalId);
    Object.entries(berthsSchedules).forEach(([id, berthSchedules]) => {
      const berthSchedulesEl = document.getElementById(`berth-ships-${id}`);
      let res = ``;
      Object.values(berthSchedules).forEach((schedule) => {
        const vessel = this.vessels[schedule.vessel];
        res += `<vessel-card
                    name="${vessel.name}"
                    view="${VesselCard.VIEW.automatic}"
                    arrival="${schedule.start}"
                    departure="${schedule.end}"
                ></vessel-card>`;
      });
      berthSchedulesEl.innerHTML += res;
    });
  }

  buildTimeline() {
    let res = '';
    const split = Array.from(Array(48).keys());
    split.forEach((val) => {
      const hour = Math.floor(val / 2);
      const minutes = val % 2 === 0 ? '00' : '30';
      res += `<div class="planner-timeline-divider">
                <p class="planner-timeline-divider-time">${hour}:${minutes}</p>
              </div>`;
    });
    return res;
  }

  buildDividers() {
    let res = '';
    const split = Array.from(Array(48).keys());
    split.forEach((val) => {
      res += `<span class="planner-berths-dividers-item">
              </span>`;
    });
    return res;
  }

  getCurrentHeight() {
    return '100px';
  }

  getCurrentTime() {
    return '8:45';
  }
}

customElements.define('planner-schedule', PlannerSchedule);
