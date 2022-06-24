// eslint-disable-next-line no-unused-vars
import IconCircle from './icon-circle.js';
import Time from '../time.js';
import PlannerSchedule from './planner-schedule.js';

class BerthClosed extends HTMLElement {
  static VIEW = {
    daily: 'daily',
    weekly: 'weekly',
  };

  constructor() {
    super();
  }

  connectedCallback() {
    this.classList.add('planner-berths-berth-closed');
    this.innerHTML = this.generateBlocks();
  }

  generateBlocks() {
    const view = document.getElementsByTagName('planner-schedule')[0].getAttribute('view');
    const open = new Time(this.getAttribute('open')).toLocal();
    const close = new Time(this.getAttribute('close')).toLocal();
    let blocks = '';

    if (view === PlannerSchedule.VIEW.daily) {
      if (open.value < close.value || close.value === 0) {
        // Two blocks, before open and after close
        if (open.value > 0) {
          // Block before open
          blocks += this.generateBlock(0, 0, open.value);
        }
        if (close.value > 0) {
          // Block after close
          blocks += this.generateBlock(0, close.value, 24);
        }
      } else {
        // One block, between close and open
        blocks += this.generateBlock(0, close.value, open.value);
      }
    } else if (view === PlannerSchedule.VIEW.weekly) {
      // TODO
    }
    return blocks;
  }

  generateBlock(top, start, end) {
    // `start` and `end` must both be between 0 (inclusive) and 24 (exclusive)
    const timeScale = this.getTimeScale();
    return `
    <div class="planner-berths-berth-closed-in" 
         style="height: ${(end === 0 ? 24 - start : end - start) * timeScale}px;
                top: ${(top + start) * timeScale}px">
      <icon-circle name="cloud-moon" view="${IconCircle.VIEW.closed}">
      </icon-circle>
    </div>
    `;
  }

  getTimeScale() {
    const view = document.getElementsByTagName('planner-schedule')[0].getAttribute('view');
    if (view === PlannerSchedule.VIEW.daily) {
      return 120;
    } else if (view === PlannerSchedule.VIEW.weekly) {
      return 20; // TODO
    }
  }
}

customElements.define('berth-closed', BerthClosed);

export default BerthClosed;
