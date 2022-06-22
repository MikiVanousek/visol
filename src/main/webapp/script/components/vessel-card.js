// eslint-disable-next-line no-unused-vars
import IconCircle from './icon-circle.js';

class VesselCard extends HTMLElement {
  static VIEW = {
    automatic: 'automatic',
    manual: 'manual',
    unscheduled: 'unscheduled',
  };

  constructor() {
    super();
  }

  connectedCallback() {
    const view = this.getAttribute('view');
    const name = this.getAttribute('name');
    // const id = this.getAttribute('id');

    this.innerHTML = `
    <div class="vessel-card view-${view}"
        data-bs-toggle="modal" 
        data-bs-target="#update-modal"
        >
        
        <div class="vessel-card-info">
          <icon-circle
            name="ship"
            view=${IconCircle.VIEW.primary}>
          </icon-circle>
          <div class="vessel-card-info-in">
            <h6 class="vessel-card-info-heading">${name}</h6>
            ${(this.hasAttribute('arrival') ||
              this.hasAttribute('departure')) ? this.getDescription() : ''}
          </div>
        </div>
        ${view === VesselCard.VIEW.manual ? this.getFooter() : ''}
    </div>
    
    
    `;
    // this.addEventListener('click', this.cardPopup);
  }

  cardPopup(e) {
    // Miki's popup for editing vessel
  }

  getDescription() {
    return `<div class="vessel-card-info-description">
    ${this.getAttribute('arrival') + ' - ' + this.getAttribute('departure') +
     ' • ' + this.capitalizeFirstLetter(this.getAttribute('view'))}
    </div>`;
  }

  getFooter() {
    return `<div class="vessel-card-footer bg-transparent border-0">
              <icon-plain
                  name="hand-paper"
                  view=${IconCircle.VIEW.primary}
              ></icon-plain>
           </div>`;
  }

  capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
  }
}

customElements.define('vessel-card', VesselCard);

export default VesselCard;
