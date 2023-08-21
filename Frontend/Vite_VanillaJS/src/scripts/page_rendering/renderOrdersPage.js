import { getOrdersPageTemplate } from '../templates/getOrdersPageTemplate.js' 
import { createLoader, deleteLoader } from './loader.js';
import { fetchOrders } from '../network/getOrders.js';
import { useStyle } from '../../styles/styles.js';
import { getDates, separateDate } from '../utils/getDates.js';
import { getImage } from '../utils/getImage.js';
import { getOrderCardTemplate } from '../templates/getOrderCardTemplate.js';
import { handleSortOrders } from '../handlers/handleSortOrders.js';
import { getModifyModalTemplate } from '../templates/getModifyModalTemplate.js';
import { handleDeleteOrder } from '../handlers/handleDeleteOrder.js';
import { handleUpdateOrder } from '../handlers/handleUpdateOrder.js';

export async function renderOrdersPage(api_urls) {
    const userId = sessionStorage.getItem("userId");  
    const mainContentDiv = document.querySelector('.main-content-component');
    mainContentDiv.innerHTML = getOrdersPageTemplate();
    
    createLoader();
    fetchOrders(api_urls.spring, userId)
    .then(data => {
        toastr.success("Orders loaded successfully! 😁");
        sessionStorage.setItem("orders", JSON.stringify(data));
        populateOrdersContainer(data, api_urls);
        createSortingButton(api_urls);
      })
      .catch(error => {
        console.log(error);
        toastr.error(error);
      })
      .finally(() =>
        deleteLoader()
      );
}

function createDeleteButton(api_urls) {
  const deleteButton = document.querySelector(".delete-button");
  deleteButton.addEventListener("click", () => {
    handleDeleteOrder(api_urls);
  })
}

function createUpdateButton(api_urls){
  const updateButton = document.querySelector(".update-button");
  updateButton.addEventListener("click", () => {
    handleUpdateOrder(api_urls);
  })
}

function createSortingButton(api_urls){
  const sortButton = document.querySelector('.sort-button');

  sortButton.addEventListener('click', () => {
    const orderList = JSON.parse(sessionStorage.getItem("orders"));
    handleSortOrders(orderList);
    populateOrdersContainer(orderList, api_urls);
  });
}
function populateOrdersContainer(orderList, api_urls){
  const ordersContainer = document.querySelector('.orders');
  ordersContainer.innerHTML = getModifyModalTemplate();
  createDeleteButton(api_urls);
  createUpdateButton(api_urls);
  createCloseModalButton();
  orderList.forEach(order => {
    const orderCard = getOrderCard(order, api_urls);
    ordersContainer.appendChild(orderCard);
    createModifyButton(orderCard);
  });
}

function createModifyButton(orderCard){
    const modifyButton = orderCard.querySelector(".modify-button");
    
    modifyButton.addEventListener('click', () => {
      const cardRect = orderCard.getBoundingClientRect();
      const modal = document.querySelector(".modal");
      modal.setAttribute("orderId", orderCard.id);
      const scrollTop = window.scrollY || document.documentElement.scrollTop;
      modal.style.top = `${cardRect.top + scrollTop}px`;
      modal.style.left = `${cardRect.left}px`;
      modal.style.display = "block";

      const order = getOrder(orderCard.id);
      console.log("creating event listener");
      console.log(order);
      console.log(order.ticketCategoryDto.description);
      addTicketDescriptions(modal, order);
      addNumberOfTickets(modal, order);

      const updateButton = document.querySelector(".update-button");
      updateButton.classList.add("unclickable");
      updateButton.classList.remove("clickable");
      console.log("test");
      window.onclick = function(event) {
        if (event.target == modal) {
          modal.style.display = 'none';
        }
      };
  });
}

function addNumberOfTickets(modal, order){
  const inputNumberOfTickets = document.querySelector("#inputNumberOfTickets");
  inputNumberOfTickets.value = order.numberOfTickets;
  sessionStorage.setItem("initialNumberOfTickets", order.numberOfTickets);
  sessionStorage.setItem("currentNumberOfTickets", order.numberOfTickets);
  inputNumberOfTickets.min = 0;
  inputNumberOfTickets.max = 20;
  const substractButton = document.querySelector(".down-button");
  const isSubstractEventListenerAdded = substractButton.__eventListenerAdded;

  if(!isSubstractEventListenerAdded){
    substractButton.__eventListenerAdded = true;
    substractButton.addEventListener("click", () => {
      if(parseInt(inputNumberOfTickets.value) > 0){
        inputNumberOfTickets.value = parseInt(inputNumberOfTickets.value) - 1;
        sessionStorage.setItem("currentNumberOfTickets", inputNumberOfTickets.value);
        testChanges();
      }
    });
  }
  
  const addButton = document.querySelector(".up-button");
  const isAddEventListenerAdded = addButton.__eventListenerAdded;
  if(!isAddEventListenerAdded){
    addButton.__eventListenerAdded = true;
    addButton.addEventListener("click", () => {
      if(parseInt(inputNumberOfTickets.value) < 20){
        inputNumberOfTickets.value = parseInt(inputNumberOfTickets.value) + 1;
        sessionStorage.setItem("currentNumberOfTickets", inputNumberOfTickets.value);
        testChanges();
      }
    });
  }
}

function addTicketDescriptions(modal, order){
  console.log("in add ticket descriptions");
  const selectTicketDescriptionInput = document.querySelector("#selectTicketDescriptionInput");
  while (selectTicketDescriptionInput.firstChild) {
    selectTicketDescriptionInput.removeChild(selectTicketDescriptionInput.firstChild);
  }
  const ticketCategoryList = order.ticketCategoryList;
  for(let i = 0; i < ticketCategoryList.length; i++){
    const ticketCategory = ticketCategoryList[i];
    const selectOption = document.createElement("option");
    selectOption.text = ticketCategory.description;
    selectOption.value = ticketCategory.description;
    if(ticketCategory.description === order.ticketCategoryDto.description){
      selectOption.selected = true;
      sessionStorage.setItem("initialDescription", ticketCategory.description);
    }
    selectTicketDescriptionInput.appendChild(selectOption);
  }
  selectTicketDescriptionInput.addEventListener("change", testChanges);
}

function testChanges(){
  const initialDescription = sessionStorage.getItem("initialDescription");
  const currentDescription = document.querySelector("#selectTicketDescriptionInput").value;

  const initialNumberOfTickets = sessionStorage.getItem("initialNumberOfTickets");
  const currentNumberOfTickets = sessionStorage.getItem("currentNumberOfTickets");

  if(initialDescription !== currentDescription
      || initialNumberOfTickets !== currentNumberOfTickets){
        const updateButton = document.querySelector(".update-button");
        updateButton.classList.add("clickable");
        updateButton.classList.remove("unclickable");
      }
  else{
    const updateButton = document.querySelector(".update-button");
    updateButton.classList.add("unclickable");
    updateButton.classList.remove("clickable");
  }
}

function getOrder(id){
  const orders = JSON.parse(sessionStorage.getItem("orders"));
  for(let i = 0; i < orders.length; i++){
    if(orders[i].orderId === id){
      return orders[i];
    }
  }
}

function getOrderCard(order, api_urls){
  const orderCard = document.createElement('div');
  orderCard.classList.add("order-card");
  orderCard.innerHTML = renderOrderDetails(order);
  orderCard.id = order.orderId;
  return orderCard;
}

function renderOrderDetails(order){
  const [startDate, startHour, endDate, endHour] = getDates(order.eventDto);
  const [orderedDate, orderedTime] = separateDate(new Date(order.orderedAt));
  const img = getImage(order.eventDto.venueType);
  return getOrderCardTemplate(img, order, [startDate, startHour], [endDate, endHour], [orderedDate, orderedTime]);
}

function createCloseModalButton(){
  const closeModalButton = document.querySelector(".close-button");
  closeModalButton.addEventListener("click", () => {
    const modal = document.querySelector(".modal");
    modal.style.display = "none";
  })
}