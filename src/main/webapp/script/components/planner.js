class PlannerSchedule extends HTMLElement {
  constructor() {
    super();
  }

  connectedCallback() {

    this.innerHTML = `
    <div class="container-md">
      <div class="planner">
        <div class="planner-timeline">
          <div class="planner-timeline-day">
            <div class="planner-timeline-day-date">
              <p class="planner-timeline-day-date-text">Thrusday 12/05/2022</p>
            </div>
            <div class="planner-timeline-day-hours">
              <div class="planner-timeline-day-hours-hour">00:00</div>
              <div class="planner-timeline-day-hours-hour">01:00</div>
              <div class="planner-timeline-day-hours-hour">02:00</div>
              <div class="planner-timeline-day-hours-hour">03:00</div>
              <div class="planner-timeline-day-hours-hour">04:00</div>
              <div class="planner-timeline-day-hours-hour">05:00</div>
              <div class="planner-timeline-day-hours-hour">06:00</div>
              <div class="planner-timeline-day-hours-hour">07:00</div>
              <div class="planner-timeline-day-hours-hour">08:00</div>
              <div class="planner-timeline-day-hours-hour">09:00</div>
              <div class="planner-timeline-day-hours-hour">10:00</div>
              <div class="planner-timeline-day-hours-hour">11:00</div>
              <div class="planner-timeline-day-hours-hour">12:00</div>
              <div class="planner-timeline-day-hours-hour">13:00</div>
              <div class="planner-timeline-day-hours-hour">14:00</div>
              <div class="planner-timeline-day-hours-hour">15:00</div>
              <div class="planner-timeline-day-hours-hour">16:00</div>
              <div class="planner-timeline-day-hours-hour">17:00</div>
              <div class="planner-timeline-day-hours-hour">18:00</div>
              <div class="planner-timeline-day-hours-hour">19:00</div>
              <div class="planner-timeline-day-hours-hour">20:00</div>
              <div class="planner-timeline-day-hours-hour">21:00</div>
              <div class="planner-timeline-day-hours-hour">22:00</div>
              <div class="planner-timeline-day-hours-hour">23:00</div>
            </div>
          </div>
          <div class="planner-timeline-day">
            <div class="planner-timeline-day-date">
              <p class="planner-timeline-day-date-text">Friday 13/05/2022</p>
            </div>
            <div class="planner-timeline-day-hours">
              <div class="planner-timeline-day-hours-hour">00:00</div>
              <div class="planner-timeline-day-hours-hour">01:00</div>
              <div class="planner-timeline-day-hours-hour">02:00</div>
              <div class="planner-timeline-day-hours-hour">03:00</div>
              <div class="planner-timeline-day-hours-hour">04:00</div>
              <div class="planner-timeline-day-hours-hour">05:00</div>
              <div class="planner-timeline-day-hours-hour">06:00</div>
              <div class="planner-timeline-day-hours-hour">07:00</div>
              <div class="planner-timeline-day-hours-hour">08:00</div>
              <div class="planner-timeline-day-hours-hour">09:00</div>
              <div class="planner-timeline-day-hours-hour">10:00</div>
              <div class="planner-timeline-day-hours-hour">11:00</div>
              <div class="planner-timeline-day-hours-hour">12:00</div>
              <div class="planner-timeline-day-hours-hour">13:00</div>
              <div class="planner-timeline-day-hours-hour">14:00</div>
              <div class="planner-timeline-day-hours-hour">15:00</div>
              <div class="planner-timeline-day-hours-hour">16:00</div>
              <div class="planner-timeline-day-hours-hour">17:00</div>
              <div class="planner-timeline-day-hours-hour">18:00</div>
              <div class="planner-timeline-day-hours-hour">19:00</div>
              <div class="planner-timeline-day-hours-hour">20:00</div>
              <div class="planner-timeline-day-hours-hour">21:00</div>
              <div class="planner-timeline-day-hours-hour">22:00</div>
              <div class="planner-timeline-day-hours-hour">23:00</div>
            </div>
          </div>
        </div>
        <div class="planner-berths">
          <div class="planner-berths-berth">
            <div class="planner-berths-berth-header">Berth 1</div>
            <div class="planner-berths-berth-ships">
              <div class="planner-berths-berth-ships-ship" style="height: 100px; top: 100px" data-bs-toggle="modal" data-bs-target="#exampleModal">
                <p class="planner-berths-berth-ships-ship-name">Vessel 1</p>
              </div>
              <div class="planner-berths-berth-ships-closed" style="height: 300px; top: 500px">
                <p class="planner-berths-berth-ships-closed-name">Berth closed</p>
              </div>
            </div>
          </div>
          <div class="planner-current" style="top: 100px"></div>
          <div class="planner-berths-berth">
            <div class="planner-berths-berth-header">Berth 2</div>
            <div class="planner-berths-berth-ships">
              <div class="planner-berths-berth-ships-ship" style="height: 180px; top: 230px" data-bs-toggle="modal" data-bs-target="#exampleModal">
                <p class="planner-berths-berth-ships-ship-name">Vessel 2</p>
              </div>
            </div>
          </div>
          <div class="planner-berths-berth">
            <div class="planner-berths-berth-header">Berth 3</div>
            <div class="planner-berths-berth-ships"></div>
            </div>
            <div class="planner-berths-berth">
              <div class="planner-berths-berth-header">Berth 4</div>
              <div class="planner-berths-berth-ships"></div>
            </div>
            <div class="planner-berths-berth">
              <div class="planner-berths-berth-header">Berth 5</div>
              <div class="planner-berths-berth-ships"></div>
            </div>
            <div class="planner-berths-berth">
              <div class="planner-berths-berth-header">Berth 6</div>
              <div class="planner-berths-berth-ships"></div>
            </div>
            <div class="planner-berths-berth">
              <div class="planner-berths-berth-header">Berth 7</div>
              <div class="planner-berths-berth-ships"></div>
            </div>
            <div class="planner-berths-berth">
              <div class="planner-berths-berth-header">Berth 8</div>
              <div class="planner-berths-berth-ships"></div>
            </div>
            <div class="planner-berths-berth">
              <div class="planner-berths-berth-header">Berth 9</div>
              <div class="planner-berths-berth-ships"></div>
            </div>
          </div>
        </div>
      </div>`
  }
}

customElements.define('planner-schedule', PlannerSchedule)
