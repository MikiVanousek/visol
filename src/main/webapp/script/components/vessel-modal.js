import VisolApi from '../api.js';

class VesselModal extends HTMLElement {
  schedule_type = 'auto';
  name;

  constructor() {
    super();
    this.name = this.getAttribute('name');
  }


  set_schedule_type(type) {
    this.schedule_type = type;
    // console.log("Changing to schedule type " + type)
    if (type === 'disabled') {
      this.getElement(`schedule-edit`).setAttribute('hidden', '');
    } else {
      this.getElement(`schedule-edit`).removeAttribute('hidden');
      for (const item of document.getElementsByClassName(`${this.name}-disabled-if-auto`)) {
        if (type === 'manual') {
          item.removeAttribute('disabled');
        } else {
          item.setAttribute('disabled', '');
        }
      }
    }
  }

  connectedCallback() {
    this.innerHTML = `<div class="modal fade" id="${this.name}-modal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">
          <b>${this.name.charAt(0).toUpperCase() + this.name.substring(1)} vessel</b></h5>
        <button class="btn-close" data-bs-dismiss="modal" type="button"></button>
      </div>
      <form id="${this.name}-modal-form">
        <div class="modal-body">
          <div class="mb-3">
            <label class="form-label" for="form-name">Name:</label>
            <input class="form-control form-control-sm" id="${this.name}-form-name"
                   name="vessel-name" placeholder="Titanic" required type="text">
          </div>

          <div class="row mb-3">
            <div class="col">
              <label class="form-label" for="${this.name}-form-arrival">Arrival:</label>
              <input class="form-control form-control-sm" id="${this.name}-form-arrival"
                     name="datetime-arrival-vessel" required type="datetime-local"
                     value="${VisolApi.formatDatetimeForInput(Date.now())}" />
            </div>
            <div class="col">
              <label class="form-label" for="${this.name}-form-deadline">Deadline:</label>
              <input class="form-control form-control-sm" id="${this.name}-form-deadline"
                     name="datetime-deadline-vessel" type="datetime-local">
            </div>
          </div>

          <div class="row mb-3">
            <div class="col">
              <label class="form-label" for="form-containers">Container amount:</label>
              <input class="form-control form-control-sm"
                     id="${this.name}-form-containers"
                     min="-1"
                     name="vessel-containers"
                     required
                     type="number">
            </div>
            <div class="col">
              <label class="form-label" for="${this.name}-form-cost_per_hour">Cost:</label>
              <input class="form-control form-control-sm" id="${this.name}-form-cost_per_hour"
                     name="vessel-cost_per_hour" required type="number">
            </div>
          </div>

          <div class="mb-3 row">
            <div class="col d-grid">
              <select-terminal name="${this.name}" prefix="vessel"></select-terminal>
            </div>
            <div class="col">
              <label class="form-label" for="form-length">Length:</label>
              <input class="form-control form-control-sm"
                     id="${this.name}-form-length"
                     name="vessel-length"
                     required
                     type="number"
                     value="0">
            </div>
          </div>

          <div class="row mb-3">
            <div class="col">
              <label class="form-label" for="form-width">Width:</label>
              <input class="form-control form-control-sm"
                     id="${this.name}-form-width"
                     name="vessel-width"
                     required
                     type="number"
                     value="0">
            </div>
            <div class="col">
              <label class="form-label" for="${this.name}-form-depth">Depth:</label>
              <input class="form-control form-control-sm"
                     id="${this.name}-form-depth"
                     name="vessel-depth"
                     required
                     type="number"
                     value="0">
            </div>
          </div>

          <div class="mb-3 row mt-2">
            <label class="label me-3 col" for="label"><b>Schedule:</b></label>
            <div class="form-check form-check-inline col">
              <input checked
                     class="form-check-input"
                     id="${this.name}-radio-auto"
                     name="schedule-manual"
                     value="false"
                     type="radio">
              <label class="form-check-label" for="${this.name}-radio-auto">
                Automatic
              </label>
            </div>
            <div class="form-check form-check-inline col">
              <input class="form-check-input"
                     id="${this.name}-radio-manual"
                     name="schedule-manual"
                     value="true"
                     type="radio">
              <label class="form-check-label" for="${this.name}-radio-manual">
                Manual
              </label>
            </div>
            <div class="form-check form-check-inline col">
              <input class="form-check-input"
                     id="${this.name}-radio-disabled"
                     name="schedule-manual"
                     value="true"
                     type="radio">
              <label class="form-check-label" for="${this.name}-radio-disabled">
                Disabled
              </label>
            </div>
          </div>

          <div class="row mb-3" id="${this.name}-schedule-edit">
            <div class="col d-grid">
              <select-berth class ="${this.name}-disabled-if-auto"> </select-berth>
            </div>
            <div class="col">
              <label class="form-label" for="form-handel">Handel time:</label>
              <input class="form-control form-control-sm ${this.name}-disabled-if-auto"
                     disabled
                     id="${this.name}-form-handel"
                     name="schedule-start"
                     required
                     type="datetime-local">
            </div>
          </div>
        </div>
        <div class="modal-footer" id="${this.name}-modal-footer-btn">
          <button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Cancel</button>
          <button class="btn btn-primary" id="${this.name}-btn-save" type="submit">Save
                                                                                        changes
          </button>
        </div>
        <div hidden id="${this.name}-modal-footer-loading">
          <div class="modal-footer d-flex justify-content-center">
            <div class="spinner-grow text-primary" role="status">
              <span class="sr-only">Loading...</span>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>`;
    // You can only attach the listeners after setting the innerHTML.
    this.attachListeners();
  }

  attachListeners() {
    this.buttons = this.getElement(`modal-footer-btn`);
    this.loader = this.getElement(`modal-footer-loading`);

    const radioIds = ['auto', 'manual', 'disabled'];
    radioIds.forEach((id) => {
      this.getElement(`radio-${id}`).addEventListener('click', () => {
        this.set_schedule_type(id);
      });
    });

    const form = this.getElement('modal-form');
    form.addEventListener('submit', (e) => {
      e.preventDefault();

      const serializedForm = this.serializeForm(form);
      const vessel = serializedForm['vessel'];
      this.hideBtnFooter();
      VisolApi.postVessel(vessel).then((response) => {
        const schedule = serializedForm['schedule'];
        if (this.schedule_type !== 'disabled') {
          // Extract the id from the url location of the vessel resource.
          const vesselId = response.headers.get('Location')
              .split('/').slice(-1)[0];
          VisolApi.putSchedule(vesselId, schedule).then((response) => {
            console.log('Schedule created successfully.');
            console.log(response);
            this.showBtnFooter();
          }).catch((e) => {
            this.showBtnFooter();
            console.log('Failed to create schedule: ', e);
          });
        } else {
          this.showBtnFooter();
        }
      }).catch((e) => {
        this.showBtnFooter();
        console.log('Failed to create vessel: ', e);
      });
    });
  }

  serializeForm(form) {
    const serializedForm = {};
    for (const prefix of ['vessel', 'schedule', 'datetime']) {
      serializedForm[prefix] = {};
    }

    const formData = new FormData(form);
    for (const [name, value] of formData) {
      if (value !== '') {
        const words = name.split('-');
        const prefix = words[0];
        const key = words[1];

        // If it is a datetime, change to timezone-neutral
        // Frankly awkward, let me know if you can do better!
        if (prefix === 'datetime') {
          // for datetimes, the object they belong to is the last word,as the first is datetime
          serializedForm[words[2]][key] = VisolApi.formatDatetimeForApi(value);
        } else {
          serializedForm[prefix][key] = value;
        }
      }
    }

    return serializedForm;
  }

  setVessel(vessel) {
    for (const key in vessel) {
      if (key === 'arrival' || key === 'deadline') {
        const formattedDate = VisolApi.formatDatetimeForInput(new Date(vessel[key]));
        console.log(formattedDate);
        this.getElement(`form-${key}`).value = formattedDate;
      } else {
        this.getElement(`form-${key}`).value = vessel[key];
      }
    }
  }

  hideBtnFooter() {
    this.buttons.setAttribute('hidden', '');
    this.loader.removeAttribute('hidden');
  }

  showBtnFooter() {
    this.loader.setAttribute('hidden', '');
    this.buttons.removeAttribute('hidden');
  }

  getElement(id) {
    return document.getElementById(this.name + '-' + id);
  }
}

customElements.define('vessel-modal', VesselModal);
