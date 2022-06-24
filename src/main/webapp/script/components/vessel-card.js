// eslint-disable-next-line no-unused-vars
import IconCircle from './icon-circle.js';
import IconPlain from './icon-plain.js';
import PlannerSchedule from './planner-schedule.js';
import Time from '../time.js';
import Timestamp from '../timestamp.js';

class VesselCard extends HTMLElement {
  static VIEW = {
    automatic: 'automatic',
    manual: 'manual',
    unscheduled: 'unscheduled',
    infeasible: 'infeasible',
  };

  constructor() {
    super();
  }

  connectedCallback() {
    this.classList.add('vessel-card', `view-${this.view}`);
    this.innerHTML = `
      <div class="vessel-card-info">
        <icon-circle
          name="ship"
          view=${this.view === VesselCard.VIEW.infeasible ?
            IconCircle.VIEW.infeasible : IconCircle.VIEW.primary}>
        </icon-circle>
        <div class="vessel-card-info-in">
          <h6 class="vessel-card-info-heading">${this.data.name}</h6>
          ${this.view !== VesselCard.VIEW.unscheduled ?
              this.getDescription() : ''}
        </div>
      </div>
      ${this.view === VesselCard.VIEW.manual ? this.getIcon() : ''}`;

    this.generateStyle();
  }

  get view() {
    if (this.hasAttribute('view')) {
      return this.getAttribute('view');
    }
    return this.schedule.manual ?
      VesselCard.VIEW.manual : VesselCard.VIEW.automatic;
  }

  get isInfeasible() {
    return this._isInfeasible;
  }

  set isInfeasible(newVal) {
    this._isInfeasible = newVal;
  }

  get data() {
    return this._data;
  }

  set data(newVal) {
    this._data = newVal;
  }

  get schedule() {
    return this._schedule;
  }

  set schedule(newVal) {
    this._schedule = newVal;
  }

  get arrival() {
    return new Timestamp(this.schedule.start).toLocal();
  }

  get departure() {
    return new Timestamp(this.schedule.expected_end).toLocal();
  }

  generateStyle() {
    if (this.view !== VesselCard.VIEW.unscheduled) {
      this.style.height = this.calculateHeight();
      this.style.top = this.calculateTop();

      if (this.isInfeasible) {
        // do some more magic
      }
    }
  }

  calculateHeight() {
    const timeScale = this.getTimeScale();

    return ((this.departure.toDate() - this.arrival.toDate()) / 1000 / 60 / 60) * timeScale + 'px';
  }

  calculateTop() {
    const view = document.getElementsByTagName('planner-schedule')[0].getAttribute('view');
    const timeScale = this.getTimeScale();

    let top = new Time(this.arrival).value * timeScale;

    if (view === PlannerSchedule.VIEW.weekly) {
      // Add day to top
      // TODO
      top += 0;
    }

    return top + 'px';
  }

  getTimeScale() {
    const view = document.getElementsByTagName('planner-schedule')[0].getAttribute('view');
    if (view === PlannerSchedule.VIEW.daily) {
      return 120;
    } else if (view === PlannerSchedule.VIEW.weekly) {
      return 20; // TODO
    }
  }

  calculateWidth() {
    const overflow = parseInt(this.getAttribute('overflow'));
    if (overflow < 3) {
      return '80%';
    }
    return (220 - (overflow - 2) * 30).toString() + 'px';
  }

  calculateLeft() {
    const overflow = parseInt(this.getAttribute('overflow'));
    if (overflow > 3) {
      return (overflow * 30).toString() + 'px';
    }
    return (overflow * 30).toString() + 'px';
  }

  formatDate(date) {
    const view = document.getElementsByTagName('planner-schedule')[0].getAttribute('view');
    if (view === PlannerSchedule.VIEW.daily) {
      return date.formatted('hh:mm');
    } else if (view === PlannerSchedule.VIEW.weekly) {
      return date.formatted('YYYY-MM-DD hh:mm');
    }
  }

  getDescription() {
    return `<div class="vessel-card-info-description">
    ${this.formatDate(this.arrival) +
    ' – ' + this.formatDate(this.departure) +
     ' • ' + this.capitalizeFirstLetter(this.view)}
    </div>`;
  }

  getIcon() {
    return `<div class="vessel-card-footer">
              <icon-plain
                  name="hand-paper"
                  view="${IconPlain.VIEW.secondary}"
              ></icon-plain>
           </div>`;
  }

  capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
  }
}

customElements.define('vessel-card', VesselCard);

export default VesselCard;
