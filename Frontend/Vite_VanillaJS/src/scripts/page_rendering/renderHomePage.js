import { getHomePageTemplate } from '../templates/getHomePageTemplate.js'
import { fetchEvents } from '../network/getEvents.js'
import { fetchEventsPaged } from '../network/getEventsPaged.js'
import { fetchSaveOrder } from '../network/saveOrder.js';
import { useStyle } from '../../styles/styles.js';
import { getImage } from '../utils/getImage.js';
import { getDates } from '../utils/getDates.js'
import { handleTicketPurchase } from '../handlers/handleTicketPurchase.js';
import { deleteLoader, createLoader } from './loader.js';
import { handleFilterEvents } from '../handlers/handleFilterEvents.js';
import { getLocationSelectTemplate } from '../templates/getLocationSelectTemplate.js';
import { getEventTypeSelectTemplate } from '../templates/getEventTypeSelectTemplate.js';
import { getEventCardTemplate } from '../templates/getEventCardTemplate.js';
import { handleRefreshEvents } from '../handlers/handleRefreshEvents.js';
import { handleLoadEvents } from '../handlers/handleLoadEvents.js';

const PAGE_SIZE = 9;
let PAGE = 1;

export async function renderHomePage(api_urls) {
    const mainContentDiv = document.querySelector('.main-content-component');
    mainContentDiv.innerHTML = getHomePageTemplate();
    
    createLoader();
    handleLoadEvents(api_urls, PAGE, PAGE_SIZE);
    createFooterInteractables(api_urls);
}

export function createFooterInteractables(api_urls){
  createPrevButton(api_urls);
  createNextButton(api_urls);
  createCurrentPageSelect(api_urls);
}

function createCurrentPageSelect(api_urls){
  const select = document.querySelector("#select-current-page");
  select.addEventListener("change", () => {
    PAGE = select.value;
    handleLoadEvents(api_urls, PAGE, PAGE_SIZE);
    select.value = PAGE;
  });
}

function createPrevButton(api_urls){
  const button = document.querySelector("#button-prev");
  button.addEventListener("click", () => {
    PAGE = parseInt(PAGE) - 1;
    handleLoadEvents(api_urls, PAGE, PAGE_SIZE);
  });
}

function createNextButton(api_urls){
  const button = document.querySelector("#button-next");
  button.addEventListener("click", () => {
    PAGE = parseInt(PAGE) + 1;
    handleLoadEvents(api_urls, PAGE, PAGE_SIZE);
  });
}

export function updateCurrentPageSelect(PAGE, totalPages){
  const select = document.querySelector("#select-current-page");
  select.innerHTML = '';
  for(let i = 1; i <= totalPages; i++){
    const option = document.createElement("option");
    option.value = i;
    option.text = i;
    if(i == PAGE){
      option.selected = true;
    }
    select.appendChild(option);
  }
}

export function createNameFilterInput(api_urls){
  const nameFilterButton = document.getElementById("inputNameFilter");
  nameFilterButton.addEventListener('input', () => {
    let name = nameFilterButton.value.trim();
    if(name === ""){
      const events = JSON.parse(sessionStorage.getItem("events"));
      populateEventsContainer(events, api_urls);
      populateLocationOptions(events);
      populateEventTypeOptions(events);
    }
    if(name.length > 3){
      const filteredEvents = filterEventsByName(name);
      populateEventsContainer(filteredEvents, api_urls);
      populateLocationOptions(filteredEvents);
      populateEventTypeOptions(filteredEvents);
    }
  });
}

function filterEventsByName(name){
  const events = JSON.parse(sessionStorage.getItem("events"));
  const filteredEvents = [];
  events.forEach(event => {
    if(event.name.startsWith(name)){
      filteredEvents.push(event);
    }
  });
  // console.log(filteredEvents);
  return filteredEvents;
}

export function populateSelectFilterOptions(){
  const events = JSON.parse(sessionStorage.getItem("events"));
  populateLocationOptions(events);
  populateEventTypeOptions(events);
}

function populateEventTypeOptions(events){
  const eventTypeInput =  document.getElementById("selectEventTypeFilterInput");
  eventTypeInput.innerHTML = getEventTypeSelectTemplate();
  const eventTypes = getEventTypes(events);
  eventTypes.forEach(eventType => {
    const eventTypeOption = createOption(eventType);
    eventTypeInput.appendChild(eventTypeOption);
  });
}

function getEventTypes(events){
  const eventTypes = new Set();
  events.forEach(event => eventTypes.add(event.type));
  return eventTypes;
}

function createOption(value){
  const optionElement = document.createElement("option");
  optionElement.text = value;
  optionElement.value = value;
  return optionElement;
}

function populateLocationOptions(events){
  const locationInput =  document.getElementById("selectLocationFilterInput");
  locationInput.innerHTML = getLocationSelectTemplate();
  const locations = getLocations(events);
  locations.forEach(location => {
    const locationOption = createOption(location);
    locationInput.appendChild(locationOption);
  });
}

function getLocations(events){
  const locations = new Set();
  events.forEach(event => locations.add(event.venue.location));
  return locations;
}

export function populateEventsContainer(eventList, api_urls){
    const eventsContainer = document.querySelector('.events');  
    eventsContainer.innerHTML = '';
    eventList.forEach(event => {
        const eventCard = getEventCard(event, api_urls);
        eventsContainer.appendChild(eventCard);
    });
}

function getEventCard(event, api_urls){
    const eventCard = document.createElement('div');
    eventCard.id = event.eventId;

    const eventWrapperClasses = useStyle('eventWrapper');
    const actionsWrapperClasses = useStyle('actionsWrapper');
    const quantityClasses = useStyle('quantity');
    const inputClasses = useStyle('input');
    const purchaseBtnClasses = useStyle('purchaseBtn');

    eventCard.classList.add(...eventWrapperClasses); 
    eventCard.innerHTML = getEventCardTemplate(event)
    const purchaseBtn = createPurchaseButton(purchaseBtnClasses);
    let actions = createActions(event, actionsWrapperClasses, quantityClasses, inputClasses, purchaseBtn, api_urls);
    eventCard.appendChild(actions);

    //footer
    const eventFooter = document.createElement('footer');
    eventFooter.appendChild(purchaseBtn);
    eventCard.appendChild(eventFooter);

    return eventCard;
}

function createPurchaseButton(purchaseBtnClasses){
  const purchase = document.createElement('button');
  purchase.classList.add(...purchaseBtnClasses);
  purchase.disabled = true;
  purchase.innerText = 'Purchase';
  return purchase;
}

function createActions(event, actionsWrapperClasses, quantityClasses, inputClasses, purchaseBtn, api_urls){
  let actions = document.createElement('div');
  actions.classList.add(...actionsWrapperClasses);
  actions.innerHTML = initActions(event.ticketCategories);

  const [quantity, input] = createQuantity_Input(quantityClasses, inputClasses, purchaseBtn);
  purchaseBtn.addEventListener('click', () => {
    let ticketCategoryId = document.getElementById(event.eventId).querySelector('#ticketType').value;
    // console.log(ticketCategoryId);
    handleTicketPurchase(ticketCategoryId, input.value, event.eventId, api_urls);
  });
  actions.appendChild(quantity);
  return actions;
}

function initActions(ticketCategories){
  const categoriesOptions = ticketCategories.map(
    (ticketCategory) =>
    `<option value=${ticketCategory.id}>${ticketCategory.description}</option>`
  );
  const ticketTypeMarkup = `
    <h2 class="purchase-sub-title">Choose ticket type</h2>
    <select id="ticketType" name="ticketType" class="">
      ${categoriesOptions.join('\n')}
    </select>
  `
  return ticketTypeMarkup;
}

function createInput_NumberOfTickets(inputClasses, purchaseBtn){
  const input = document.createElement('input');
  input.classList.add(...inputClasses);
  input.type = 'number';
  input.min = '0';
  input.value = '0';
  return input;
}

function createQuantity_Input(quantityClasses, inputClasses, purchaseBtn){
  const quantity = document.createElement('div');
  quantity.classList.add(...quantityClasses);
  quantity.classList.add("input-update");
  const input = createInput_NumberOfTickets(inputClasses, purchaseBtn);
  const downButton = document.createElement('div');
  downButton.classList.add("down-button");
  downButton.textContent = "-";
  const isSubstractEventListenerAdded = downButton.__eventListenerAdded;

  if(!isSubstractEventListenerAdded){
    downButton.__eventListenerAdded = true;
    downButton.addEventListener("click", () => {
      if(parseInt(input.value) > 0){
        input.value = parseInt(input.value) - 1;
        const currentQuantity = parseInt(input.value);
        if(currentQuantity > 0){
          purchaseBtn.disabled = false;
        } else {
          purchaseBtn.disabled = true;
        }
      }
    });
  }



  quantity.appendChild(downButton)
  quantity.appendChild(input);
  const upButton = document.createElement('div');
  upButton.classList.add("up-button");
  upButton.textContent = "+";
  const isAddEventListenerAdded = upButton.__eventListenerAdded;
  if(!isAddEventListenerAdded){
    upButton.__eventListenerAdded = true;
    upButton.addEventListener("click", () => {
      if(parseInt(input.value) < 20){
        input.value = parseInt(input.value) + 1;
        const currentQuantity = parseInt(input.value);
        if(currentQuantity > 0){
          purchaseBtn.disabled = false;
        } else {
          purchaseBtn.disabled = true;
        }
        // sessionStorage.setItem("currentNumberOfTickets", inputNumberOfTickets.value);
        // testChanges();
      }
    });
  }
  input.addEventListener('blur', () => {
    if(!input.value){
      input.value = 0;
    }
  });
  input.addEventListener('input', () => {
    const currentQuantity = parseInt(input.value);
    if(currentQuantity > 0){
      purchaseBtn.disabled = false;
    } else {
      purchaseBtn.disabled = true;
    }
  });
  quantity.appendChild(upButton)
  return [quantity, input];
}

export function createFilterButton(api_urls){
  const filterButton = document.querySelector('.filter-button');

  filterButton.addEventListener('click', () => {
    handleFilterEvents(api_urls, populateEventsContainer, populateLocationOptions, populateEventTypeOptions);
  });
}

export function createRefreshButton(api_urls){
  const refreshButton = document.querySelector('.refresh-button');
  refreshButton.addEventListener('click', () => {
    handleRefreshEvents(api_urls);
  });
}