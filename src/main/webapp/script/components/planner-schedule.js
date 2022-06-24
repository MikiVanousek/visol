import FullButton from './full-button.js';
import IconCircle from './icon-circle.js';
import VesselCard from './vessel-card.js';
import CurrentTime from './current-time.js';
import VisolApi from '../api.js';

class PlannerSchedule extends HTMLElement {
  static VIEW = {
    daily: 'daily',
    weekly: 'weekly',
  };

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

  get berthsId() {
    return 'planner-berths-' + this.terminalId;
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
                  data-bs-target="#create-modal">
              </icon-circle>
          </div>
          <div class="planner-schedule-in">
            <!-- TODO only add the current time if the view contains the current date. remove it if it no longer does -->
            <current-time view="${CurrentTime.VIEW.daily}"></current-time>
            <div class="planner-timeline">
               ${this.buildTimeline()}
            </div>
            <div class="planner-berths" id="${this.berthsId}">
              <div class="planner-berths-dividers">
                  ${this.buildDividers()}
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
    dropDown.data = {};
    dropDown.data[PlannerSchedule.VIEW.daily] = 'Daily';
    dropDown.data[PlannerSchedule.VIEW.weekly] = 'Week';
    dropDown.active = dropDown.data[PlannerSchedule.VIEW.daily];
    dropDown.callBack = this.changeView;
    dropDown.render();
  }

  changeView(newVal) {
    console.log(newVal);
  }

  async loadBerths() {
    const schedule =
      document.getElementById(this.berthsId);
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
      Object.values(berthSchedules).forEach((schedule) => {
        const vessel = this.vessels[schedule.vessel];
        const vesselEl = document.createElement('vessel-card');
        vesselEl.data = vessel;
        vesselEl.schedule = schedule;
        berthSchedulesEl.appendChild(vesselEl);
      });
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
}

customElements.define('planner-schedule', PlannerSchedule);

export default PlannerSchedule;
