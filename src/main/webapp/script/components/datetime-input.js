import Timestamp from '../timestamp.js';

class DatetimeInput extends HTMLInputElement {
  constructor() {
    super();
    this.setAttribute('type', 'datetime-local');

    // Format
    this._format = 'YYYY-MM-DDThh:mm:ss';
    if (this.hasAttribute('no-seconds')) {
      this.setAttribute('step', '60');
      this._format = 'YYYY-MM-DDThh:mm';
    }

    // Other attributes
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
    super.value = new Timestamp(timestamp).toLocal().formatted(this._format);
  }
}

customElements.define('datetime-input', DatetimeInput, {extends: 'input'});
