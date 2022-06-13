import Requets from "../requests.js"
class VesselModal extends HTMLElement {
  constructor() {
    super();
  }

   set_schedule_type(type) {
    if (type==="disabled") {
      document.getElementById("schedule-edit").setAttribute("hidden","")
    } else {
      document.getElementById("schedule-edit").removeAttribute("hidden")
      for (let item of document.getElementsByClassName("disabled-if-auto")) {
        if (type === "manual") {
          item.removeAttribute("disabled")
        } else {
          item.setAttribute("disabled", "")
        }
      }
    }
  }

  connectedCallback() {
    this.innerHTML = `
<div class="modal fade" id="${this.getAttribute('name')}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel"><b>Vessel information</b></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <form id="modal-form">
        <div class="modal-body">
          <div class="mb-3">
            <label for="form-name" class="form-label">Name:</label>
            <input type="text" class="form-control form-control-sm" id="form-name" name="name" placeholder="Titanic" required>
          </div>

          <div class="row mb-3">
            <div class="col">
              <label for="form-arrival" class="form-label">Arrival:</label>
              <input type="datetime-local" class="form-control form-control-sm" id="form-arrival" name="arrival" required>
            </div>
            <div class="col">
              <label for="form-deadline" class="form-label">Deadline:</label>
              <input type="datetime-local" class="form-control form-control-sm" id="form-deadline" name="deadline">
            </div>
          </div>

          <div class="row mb-3">
            <div class="col">
              <label for="form-containers" class="form-label">Container amount:</label>
              <input type="number" min="-1" class="form-control form-control-sm" id="form-containers"
                     name="container_amount" required>
            </div>
            <div class="col">
              <label for="form-cost" class="form-label">Cost:</label>
              <input type="number" class="form-control form-control-sm" id="form-cost" name="cost" required>
            </div>
          </div>

          <div class="dropdown mb-3 row">
            <div class="col d-grid">
              <label for="dropdown-terminal" class="form-label me-3"><b>Terminal:</b></label>
              <button class="btn btn-outline-secondary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown"
                      id="dropdown-terminal">
                Not selected...
              </button>
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">Terminal 1</a></li>
                <li><a class="dropdown-item" href="#">Terminal 2</a></li>
                <li><a class="dropdown-item" href="#">Terminal 3</a></li>
              </ul>
            </div>
            <div class="col">
              <label for="form-length" class="form-label">Length:</label>
              <input type="number" class="form-control form-control-sm" id="form-length" required>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col">
              <label for="form-width" class="form-label">Width:</label>
              <input type="number" class="form-control form-control-sm" id="form-width" name="width" required>
            </div>
            <div class="col">
              <label for="form-depth" class="form-label">Depth:</label>
              <input type="number" class="form-control form-control-sm" id="form-depth" name="width" required>
            </div>
          </div>

          <div class="mb-3 row mt-2">
            <label class="label me-3 col" for="label"><b>Schedule:</b></label>
            <div class="form-check form-check-inline col">
              <input class="form-check-input" type="radio" name="flexRadioDefault" id="radio-auto" checked>
              <label class="form-check-label" for="radio-auto">
                Automatic
              </label>
            </div>
            <div class="form-check form-check-inline col">
              <input class="form-check-input" type="radio" name="flexRadioDefault" id="radio-manual">
              <label class="form-check-label" for="radio-manual">
                Manual
              </label>
            </div>
            <div class="form-check form-check-inline col">
              <input class="form-check-input" type="radio" name="flexRadioDefault" id="radio-disabled">
              <label class="form-check-label" for="radio-disabled">
                Disabled
              </label>
            </div>
          </div>

          <div class="row mb-3" id="schedule-edit">
            <div class="col">
              <label for="form-berth" class="form-label">Berth:</label>
              <input type="number" class="form-control form-control-sm disabled-if-auto" id="form-berth" disabled required>
            </div>
            <div class="col">
              <label for="form-handel" class="form-label">Handel:</label>
              <input type="date" class="form-control form-control-sm disabled-if-auto" id="form-handel" disabled required>
            </div>
          </div>
        </div>
        <div class="modal-footer" id="modal-footer-btn">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
          <button type="submit" class="btn btn-primary" id="btn-save">Save changes</button>
        </div>
        <div id="modal-footer-loading" hidden>
          <div class="modal-footer d-flex justify-content-center">
            <div class="spinner-grow text-primary" role="status">
              <span class="sr-only">Loading...</span>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
	`
    let ids = ["auto", "manual", "disabled"]
    ids.forEach(id => {
      document.getElementById(`radio-${id}`).addEventListener("click", () => {
        this.set_schedule_type(id)
      })
    })
    document.getElementById('modal-form').addEventListener('submit', e => {
      e.preventDefault()
      const form = document.getElementById("modal-form")
      const formData = new FormData(form);
      const vessel = {};

      for (const [name, value] of formData) {
        if (value !== "") {
          vessel[name] = value;
        }
      }
      console.log(JSON.stringify(vessel));

      let buttons = document.getElementById("modal-footer-btn")
      let loader = document.getElementById("modal-footer-loading")
      buttons.setAttribute("hidden", "")
      loader.removeAttribute("hidden")
      Requets.postData("/vessels", vessel).then()
    })
  }
}

customElements.define('vessel-modal', VesselModal);
