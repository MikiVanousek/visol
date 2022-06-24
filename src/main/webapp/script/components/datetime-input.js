import Timestamp from '../timestamp.js';

class DatetimeInput extends HTMLInputElement {
  constructor() {
    super();
    this.setAttribute('type', 'datetime-local');
    if (this.hasAttribute('now')) {
      this.value = new Date();
    }
  }

  get value() {
    try {
      return new Timestamp(super.value).toUTC().formatted();
    } catch (error) {
      return undefined;
    }
  }

  set value(timestamp) {
    super.value = new Timestamp(timestamp).toLocal().formatted('YYYY-MM-DDThh:mm:ss');
  }
}

customElements.define('datetime-input', DatetimeInput, {extends: 'input'});
