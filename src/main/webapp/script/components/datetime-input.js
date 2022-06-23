import VisolApi from '../api.js';

class DatetimeInput extends HTMLInputElement {
  constructor() {
    super();
    this.setAttribute('type', 'datetime-local');
    if (this.hasAttribute('now')) {
      this.value = new Date();
    }
  }

  get value() {
    if (super.value === '') {
      return undefined;
    }
    return VisolApi.formatDatetimeForApi(super.value);
  }

  set value(timestamp) {
    super.value = VisolApi.formatDatetimeForInput(new Date(timestamp));
  }
}

customElements.define('datetime-input', DatetimeInput, {extends: 'input'});
