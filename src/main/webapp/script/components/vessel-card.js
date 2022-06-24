// eslint-disable-next-line no-unused-vars
import IconCircle from './icon-circle.js';
import IconPlain from './icon-plain.js';
import PlannerSchedule from './planner-schedule.js';
import Time from '../time.js';

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
    const view = this.getAttribute('view');
    const name = this.getAttribute('name');
    // const id = this.getAttribute('id');

    this.innerHTML = `
    <div class="vessel-card view-${view}"
        data-bs-toggle="modal" 
        data-bs-target="#update-modal"
        style="${this.generateStyle()}"
        >
        
        <div class="vessel-card-info">
          <icon-circle
            name="ship"
            view="${view === VesselCard.VIEW.infeasible ?
        IconCircle.VIEW.infeasible : IconCircle.VIEW.primary}">
          </icon-circle>
          <div class="vessel-card-info-in">
            <h6 class="vessel-card-info-heading">${name}</h6>
            ${(this.hasAttribute('arrival') ||
        this.hasAttribute('departure')) ? this.getDescription() : ''}
          </div>
        </div>
        ${view === VesselCard.VIEW.manual ||
    view === VesselCard.VIEW.infeasible ?
        this.getFooter() : ''}
    </div>
    
    
    `;
  }

  generateStyle() {
    const view = this.getAttribute('view');

    if (view === VesselCard.VIEW.manual || view === VesselCard.VIEW.automatic) {
      return 'height: ' + this.calculateHeight() + ';' +
          'top: ' + this.calculateTop() + ';';
    } else if (view === (VesselCard.VIEW.infeasible)) {
      return 'height: ' + this.calculateHeight() + ';' +
          'top: ' + this.calculateTop() + ';' +
          'width: ' + this.calculateWidth() + ';' +
          'left: ' + this.calculateLeft() + ';';
    }
  }

  calculateHeight() {
    const timeScale = this.getTimeScale();
    /* TODO
    const arrival = new Timestamp(this.getAttribute('arrival'));
    const departure = new Timestamp(this.getAttribute('departure'));

    return ((departure.toDate() - arrival.toDate()) / 60000) * timeScale;
     */

    return (
      new Time(this.getAttribute('departure')).value -
        new Time(this.getAttribute('arrival')).value
    ) * timeScale;
  }

  calculateTop() {
    const view = document.getElementsByTagName('planner-schedule')[0].getAttribute('view');
    const timeScale = this.getTimeScale();
    // TODO const arrival = new Timestamp(this.getAttribute('arrival')).toLocal();
    const arrival = this.getAttribute('arrival');


    const top = new Time(arrival).value * timeScale;

    if (view === PlannerSchedule.VIEW.weekly) {
      // Add day to top
      // TODO
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

  getMinutes(str) {
    const strs = str.split(':');
    return parseInt(strs[0]) * 60 + parseInt(strs[1]);
  }


  getDescription() {
    return `<div class="vessel-card-info-description">
    ${this.getAttribute('arrival') + ' - ' + this.getAttribute('departure') +
    ' • ' + this.capitalizeFirstLetter(this.getAttribute('view'))}
    </div>`;
  }

  getFooter() {
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
