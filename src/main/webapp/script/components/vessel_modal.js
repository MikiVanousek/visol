import Requests from "../requests.js"
class VesselModal extends HTMLElement {
  schedule_type = "auto"
  terminals
  form_id

  constructor() {
    super();
     this.terminals = Requests.getData(`/ports/${Requests.portId}/terminals`)
     this.form_id = this.getAttribute('name') + '-modal-form'
  }

   set_schedule_type(type) {
    this.schedule_type = type
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
    this.terminals.then(t => {
      this.buildModal(t)
    })
  }
  buildModal(terminals) {
    this.innerHTML = `
<div class="modal fade" id="${this.getAttribute('name')}-modal" tabindex="-1">
  <div class="modal-dialog" >
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title"><b>Vessel information</b></h5>
        <button class="btn-close" data-bs-dismiss="modal" type="button"></button>
      </div>
      <form id="${this.form_id}">
        <div class="modal-body">
          <div class="mb-3">
            <label class="form-label" for="form-name">Name:</label>
            <input class="form-control form-control-sm" id="form-name" name="vessel-name" placeholder="Titanic" required
                   type="text">
          </div>

          <div class="row mb-3">
            <div class="col">
              <label class="form-label" for="form-arrival">Arrival:</label>
              <input class="form-control form-control-sm" id="form-arrival" name="vessel-arrival" required
                     type="datetime-local" value="${VesselModal.nowJsonString()}">
            </div>
            <div class="col">
              <label class="form-label" for="form-deadline">Deadline:</label>
              <input class="form-control form-control-sm" id="form-deadline" name="vessel-deadline" type="datetime-local">
            </div>
          </div>

          <div class="row mb-3">
            <div class="col">
              <label class="form-label" for="form-containers">Container amount:</label>
              <input class="form-control form-control-sm" id="form-containers" min="-1" name="vessel-container_amount"
                     required type="number">
            </div>
            <div class="col">
              <label class="form-label" for="form-cost">Cost:</label>
              <input class="form-control form-control-sm" id="form-cost" name="vessel-cost" required type="number">
            </div>
          </div>

          <div class="mb-3 row">
            <div class="col d-grid">
              <label class="form-label me-3" for="select-terminal"><b>Terminal:</b></label>
              <select class="form-select form-select-sm" name="vessel-destination_terminal"> 
                ${Object.keys(terminals).map(i => `<option value="${i}">${terminals[i]['name']}</option>`).join('\n')}
              </select>
              </div>
              <div class="col">
                <label class="form-label" for="form-length">Length:</label>
              <input class="form-control form-control-sm" id="form-length" name="dimension-length" required type="number" value="0">
            </div>
          </div>

          <div class="row mb-3">
            <div class="col">
              <label class="form-label" for="form-width">Width:</label>
              <input class="form-control form-control-sm" id="form-width" name="dimension-width" required type="number" value="0">
            </div>
            <div class="col">
              <label class="form-label" for="form-depth">Depth:</label>
              <input class="form-control form-control-sm" id="form-depth" name="dimension-depth" required type="number" value="0">
            </div>
          </div>

          <div class="mb-3 row mt-2">
            <label class="label me-3 col" for="label"><b>Schedule:</b></label>
            <div class="form-check form-check-inline col">
              <input checked class="form-check-input" id="radio-auto" name="radio-auto" type="radio">
              <label class="form-check-label" for="radio-auto">
                Automatic
              </label>
            </div>
            <div class="form-check form-check-inline col">
              <input class="form-check-input" id="radio-manual" name="radio-manual" type="radio">
              <label class="form-check-label" for="radio-manual">
                Manual
              </label>
            </div>
            <div class="form-check form-check-inline col">
              <input class="form-check-input" id="radio-disabled" name="radio-disabled" type="radio">
              <label class="form-check-label" for="radio-disabled">
                Disabled
              </label>
            </div>
          </div>

          <div class="row mb-3" id="schedule-edit">
            <div class="col">
              <label class="form-label" for="form-berth">Berth:</label>
              <input class="form-control form-control-sm disabled-if-auto" disabled id="form-berth" name="schedule-berth" required
                     type="number">
            </div>
            <div class="col">
              <label class="form-label" for="form-handel">Handel:</label>
              <input class="form-control form-control-sm disabled-if-auto" disabled id="form-handel" name="schedule-handel" required
                     type="datetime-local">
            </div>
          </div>
        </div>
        <div class="modal-footer" id="modal-footer-btn">
          <button class="btn btn-secondary" data-bs-dismiss="modal" type="button">Cancel</button>
          <button class="btn btn-primary" id="btn-save" type="submit">Save changes</button>
        </div>
        <div hidden id="modal-footer-loading">
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
    let radio_ids = ["auto", "manual", "disabled"]
    radio_ids.forEach(id => {
      document.getElementById(`radio-${id}`).addEventListener("click", () => {
        this.set_schedule_type(id)
      })
    })

    const form = document.getElementById(this.form_id)
    form.addEventListener('submit', e => {
      e.preventDefault()

      const serializedForm = {};
      for (const prefix of ["vessel", "schedule", "dimension", "radio"]){
        serializedForm[prefix] = {}
      }

      const formData = new FormData(form);
      for (const [name, value] of formData) {
        if (value !== "") {
          let words = name.split("-")
          let prefix = words[0]
          let key = words[1]
          serializedForm[prefix][key] = value;
        }
      }

      let vessel = serializedForm["vessel"]
      vessel["dimension"] = serializedForm["dimension"]
      this.hideBtnFooter()
      console.log(serializedForm)
      Requests.postData("/vessels", vessel).then(response => {
        console.log(response)
        this.showBtnFooter()
        let schedule = serializedForm["schedule"]
        console.log(schedule)
      })
    })
  }

  generateSelect(terminals) {
  }

  hideBtnFooter() {
    let buttons = document.getElementById("modal-footer-btn")
    let loader = document.getElementById("modal-footer-loading")
    buttons.setAttribute("hidden", "")
    loader.removeAttribute("hidden")
  }
  showBtnFooter() {
    let buttons = document.getElementById("modal-footer-btn")
    let loader = document.getElementById("modal-footer-loading")
    loader.setAttribute("hidden", "")
    buttons.removeAttribute("hidden")
  }

  static nowJsonString() {
    var tzoffset = new Date().getTimezoneOffset() * 60000 //offset in milliseconds
    return new Date(Date.now() - tzoffset).toJSON().substring(0,16)
  }
}

customElements.define('vessel-modal', VesselModal);
