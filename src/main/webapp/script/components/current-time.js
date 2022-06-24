// eslint-disable-next-line no-unused-vars
import Time from '../time.js';

class CurrentTime extends HTMLElement {
  static VIEW = {
    daily: 'daily',
    weekly: 'weekly',
  };

  constructor() {
    super();
  }

  connectedCallback() {
    this.classList.add('planner-current-wrap');
    this.innerHTML = `
      <div class="planner-current">
        <div class="planner-current-in">
            <p class="planner-current-time"></p>
        </div>
      </div>
    `;
    this.updateTime();
    const self = this;

    // Make it run at the top of every minute
    setTimeout(function() {
      self.updateTime();
      setInterval(function() {
        self.updateTime();
      }, 1000 * 60);
    }, 1000 * (60 - new Date().getSeconds()));
  }

  updateTime() {
    const view = this.getAttribute('view');
    const timeScale = this.getTimeScale();
    const time = new Time(new Date()).toLocal();

    if (view === CurrentTime.VIEW.daily) {
      this.querySelector('.planner-current-time').innerHTML =
          time.formatted('h:mm');

      const top = 60 + time.value * timeScale;
      this.querySelector('.planner-current').style.top = top + 'px';
    } else if (view === CurrentTime.VIEW.weekly) {
      // TODO
    }
  }

  scrollToMiddle() {
    const timeScale = this.getTimeScale();
    const time = new Time(new Date()).toLocal();
    const plannerHeight = document.querySelector('.planner').offsetHeight;
    document.querySelector('.planner-schedule').scroll(0,
        (60 + time.value * timeScale) - (plannerHeight / 2));
  }

  getTimeScale() {
    const view = this.getAttribute('view');
    if (view === CurrentTime.VIEW.daily) {
      return 120;
    } else if (view === CurrentTime.VIEW.weekly) {
      return 20; // TODO
    }
  }
}

customElements.define('current-time', CurrentTime);

export default CurrentTime;
