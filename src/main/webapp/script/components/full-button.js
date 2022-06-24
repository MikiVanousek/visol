import IconPlain from './icon-plain.js';

class FullButton extends HTMLElement {
  static VIEW = {
    primary: 'primary',
    secondary: 'secondary',
    disabled: 'disabled',
  };

  static SIZE = {
    medium: 'medium',
    large: 'large',
  };

  constructor() {
    super();
  }

  connectedCallback() {
    const children = this.innerHTML;
    const icon = this.hasAttribute('icon') ?
      `<icon-plain 
            name="${this.getAttribute('icon')}" 
            view="${IconPlain.VIEW.button}"></icon-plain>` : ``;
    const size = this.hasAttribute('size') ?
      this.getAttribute('size') : FullButton.SIZE.medium;
    const onclick = this.hasAttribute('onclick') ? `onclick="${this.getAttribute('onclick')}"` : ``;
    this.innerHTML = `
        <button 
            class="full-button view-${this.getAttribute('view')} size-${size}"
            ${onclick} type="button">${children} ${icon}</button>
    `;
  }
}

customElements.define('full-button', FullButton);

export default FullButton;
