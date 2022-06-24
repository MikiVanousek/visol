import VisolApi from '../api.js';

class VesselModal extends HTMLElement {
  schedule_type;
  // ID prefix
  vesselApiEndpoint;
  name;

  constructor() {
    super();
    this.name = this.getAttribute('name');
  }

  setScheduleType(type) {
    this.schedule_type = type;
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
    this.innerHTML = `
<div class="modal fade" id="${this.name}-modal" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">
          <b>${this.name.charAt(0).toUpperCase() + this.name.substring(1)} vessel</b>
        </h5>
        <button class="btn-close" data-bs-dismiss="modal" type="button"></button>
      </div>
      <form id="${this.name}-modal-form">
        <div class="modal-body">
          <div class="mb-3">
            <label class="form-label" for="form-name">Name:</label>
            <input
              class="form-control form-control-sm"
              id="${this.name}-form-name">
          </div>

          <div class="row mb-3">
            <div class="col">
              <label class="form-label" for="${this.name}-form-arrival">Arrival:</label>
              <input id="${this.name}-form-arrival"
                     is="datetime-input"
                     class="form-control form-control-sm"
                     now
                     required>
            </div>
            <div class="col">
              <label class="form-label" for="${this.name}-form-deadline">Deadline:</label>
              <input is="datetime-input"
                     class="form-control form-control-sm"
                     id="${this.name}-form-deadline">
            </div>
          </div>

          <div class="row mb-3">
            <div class="col">
              <label class="form-label" for="form-containers">Container amount:</label>
              <input class="form-control form-control-sm"
                     id="${this.name}-form-containers"
                     min="-1"
                     required
                     type="number">
            </div>
<!--            TODO Miki min-->
            <div class="col">
              <label class="form-label" for="${this.name}-form-cost_per_hour">Cost:</label>
              <input
                class="form-control form-control-sm"
                id="${this.name}-form-cost_per_hour"
                type="number"
              >
            </div>
          </div>

          <div class="mb-3 row">
            <div class="col">
              <label class="form-label" for="${this.name}-form-width"><b>Terminal:</b></label>
              <select is="select-terminal" 
                      class="form-select form-select-sm"
                      id="${this.name}-form-destination"
              ></select>
            </div>
            <div class="col">
              <label class="form-label" for="form-length">Length:</label>
              <input class="form-control form-control-sm"
                     id="${this.name}-form-length"
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
                     required
                     type="number"
                     value="0">
            </div>
            <div class="col">
              <label class="form-label" for="${this.name}-form-depth">Depth:</label>
              <input class="form-control form-control-sm"
                     id="${this.name}-form-depth"
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
                     name="${this.name}-radio"
                     value="false"
                     type="radio">
              <label class="form-check-label" for="${this.name}-radio-auto">
                Automatic
              </label>
            </div>
            <div class="form-check form-check-inline col">
              <input class="form-check-input"
                     id="${this.name}-radio-manual"
                     name="${this.name}-radio"
                     value="true"
                     type="radio">
              <label class="form-check-label" for="${this.name}-radio-manual">
                Manual
              </label>
            </div>
            <div class="form-check form-check-inline col">
              <input class="form-check-input"
                     id="${this.name}-radio-disabled"
                     name="${this.name}-radio"
                     value="true"
                     type="radio">
              <label class="form-check-label" for="${this.name}-radio-disabled">
                Disabled
              </label>
            </div>
          </div>
          
          <div class="row mb-3" id="${this.name}-schedule-edit">
            <div class="col d-grid">
              <label class="form-label" for="${this.name}-form-berth">Berth:</label>
              <select is="select-berth" 
                      id="${this.name}-form-berth"
                      class="form-select form-select-sm ${this.name}-disabled-if-auto"
                      terminal="4"
              > </select>
            </div>
            <div class="col">
              <label class="form-label" for="form-start">Handle time:</label>
              <input id="${this.name}-form-start"
                     is="datetime-input"
                      class="form-control form-control-sm ${this.name}-disabled-if-auto"
                     disabled
                     required
                   >
            </div>
          </div>
        </div>
        <hr></hr>
        <div class="mx-3 my-4">
          <div class="row " id="${this.name}-modal-footer-btn">
            <div class="col d-flex justify-content-end">
              <button class="btn btn-secondary me-2" data-bs-dismiss="modal" type="button">
                Cancel
              </button>
              <button class="btn btn-primary" id="${this.name}-btn-save" type="submit">
                Save changes
              </button>
            </div>
          </div>
          <div hidden id="${this.name}-modal-footer-loading">
            <div class="d-flex justify-content-center">
              <div class="spinner-grow text-primary" role="status">
                <span class="sr-only">Loading...</span>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>`;
    this.setScheduleType('auto');
    // You can only attach the listeners after setting the innerHTML.
    this.attachListeners();
  }

  attachListeners() {
    this.buttons = this.getElement(`modal-footer-btn`);
    this.loader = this.getElement(`modal-footer-loading`);

    const radioIds = ['auto', 'manual', 'disabled'];
    radioIds.forEach((id) => {
      this.getElement(`radio-${id}`).addEventListener('click', () => {
        this.setScheduleType(id);
      });
    });

    const form = this.getElement('modal-form');
    form.addEventListener('submit', (e) => {
      e.preventDefault();
      this.submitContents();
    });

    const destinationSelect = this.getElement('form-destination');
    destinationSelect.addEventListener('change', (e) => {
      this.getElement('form-berth').setTerminal(destinationSelect.value);
    });
  }

  serialize(keys) {
    const res = {};
    keys.forEach((key, _) => {
      const el = this.getElement(`form-${key}`);
      if (!el.disabled) {
        res[key] = el.value;
      }
    });
    return res;
  }

  getVessel() {
    const vesselKeys = ['name', 'arrival', 'deadline', 'containers', 'cost_per_hour', 'destination', 'length', 'width', 'depth'];
    return this.serialize(vesselKeys);
  }

  getSchedule() {
    const scheduleKeys = ['berth', 'start'];
    const schedule = this.serialize(scheduleKeys);
    schedule['manual'] = this.schedule_type === 'manual';
    return schedule;
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
    const el = document.getElementById(this.name + '-' + id);
    if (el === null) throw new Error(`Failed to get element with id ${this.name}-${id}`);
    return el;
  }

  submitContents() {
    const vessel = this.getVessel();
    this.hideBtnFooter();
    this.vesselApiEndpoint(vessel).then((response) => {
      if (this.schedule_type !== 'disabled') {
        // Extract the id from the url location of the vessel resource.
        const vesselId = response.headers.get('Location')
            .split('/').slice(-1)[0];
        const schedule = this.getSchedule();
        VisolApi.putSchedule(vesselId, schedule).then((response) => {
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
  }
}

class UpdateModal extends VesselModal {
  name = 'update';
  vesselId;
  vesselApiEndpoint = (vessel) => {
    if (this.vesselId === null) {
      throw new Error('You have to set a vesselId when opening an update form.');
    }
    return VisolApi.putVessel(this.vesselId, vessel);
  };

  fillIn(object) {
    // eslint-disable-next-line guard-for-in
    for (const key in object) {
      try {
        this.getElement(`form-${key}`).value = object[key];
      } catch (_) {
        console.log(`Warning: ${key} has no filed in the form.`);
      }
    }
  }

  setSchedule(schedule) {
    if (schedule === null || !('manual' in schedule)) {
      this.getElement('radio-disabled').click();
    } else {
      this.fillIn(schedule);
      this.getElement('radio-' + (schedule['manual'] === 'true' ? 'manual' : 'auto')).click();
    }
  }
}

class CreateModal extends VesselModal {
  name = 'create';
  vesselApiEndpoint = VisolApi.postVessel;
}

customElements.define('update-modal', UpdateModal);
customElements.define('create-modal', CreateModal);
