export function getOrderCardTemplate(img, order, startDateTime, endDateTime, lastUpdateDateTime){
    const [startDate, startHour] = startDateTime;
    const [endDate, endHour] = endDateTime;
    const [lastUpdateDate, lastUpdateTime] = lastUpdateDateTime;
    return `
      <div class="event-image">
        <div class="img-order-container">
          <img src="${img}" alt="event image">
        </div>
      </div>
      <div class="event-details">
        <div class="event-name">${order.eventDto.name}</div>
        <div class="event-location">${order.eventDto.location}</div>
      </div>
      <div class="additional-details">
        <div class="date-details">
          <div class="date-label">
            <div class="label-text">From:</div>
            <div class="from-date">${startDate} / ${startHour}</div>
          </div>
          <div class="date-label">
            <div class="label-text">To:</div>
            <div class="end-date">${endDate} / ${endHour}</div>
          </div>
          <div class="date-label">
            <div class="label-text">Last update:</div>
            <div class="last-update-date">${lastUpdateDate} / ${lastUpdateTime}</div>
          </div>
        </div>
        <div class="ticket-details">
          <div class="detail-label">${order.ticketCategoryDto.description}</div>
          <div class="tickets-amount">Amount: ${order.numberOfTickets}</div>
          <div class="total-price">Total: £${order.totalPrice}</div>
        </div>
        </div>
        <div class="modify-options">
          <div class="modify-button">
            <button>Modify</button>
          </div>
        </div>
      `;
}