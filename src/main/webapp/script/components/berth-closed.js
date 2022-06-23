// eslint-disable-next-line no-unused-vars
import IconCircle from './icon-circle.js';

class Time {
  constructor(string) {
    const numbers = string.split(':').map(Number);
    if (numbers.length !== 3) {
      throw new Error('Invalid time string');
    }
    this._hours = numbers[0];
    this._minutes = numbers[1];
    this._seconds = numbers[2];

    // Note that in PostgreSQL, 24:00:00 is considered to be 00:00:00.
    if (this._hours < 0 || this._hours > 23 ||
      this._minutes < 0 || this._minutes > 59 ||
      this._seconds < 0 || this._seconds > 59) {
      throw new Error('Invalid time');
    }
  }

  get value() {
    return this._hours + this._minutes / 60 + this._seconds / 3600;
  }
}

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
    const view = this.getAttribute('view');
    const open = new Time(this.getAttribute('open'));
    const close = new Time(this.getAttribute('close'));
    let blocks = '';

    if (view === BerthClosed.VIEW.daily) {
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
    } else if (view === BerthClosed.VIEW.weekly) {
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
    const view = this.getAttribute('view');
    if (view === BerthClosed.VIEW.daily) {
      return 120;
    } else if (view === BerthClosed.VIEW.weekly) {
      return 20; // TODO
    }
  }
}

customElements.define('berth-closed', BerthClosed);

export default BerthClosed;
