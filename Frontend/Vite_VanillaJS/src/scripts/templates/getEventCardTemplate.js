import { getDates } from "../utils/getDates";
import { getImage } from "../utils/getImage";


export function getEventCardTemplate(event){
    event.img = getImage(event.venue.type);
    let [startDate, startHour, endDate, endHour] = getDates(event);
    return `
      <header>
        <h1 class="event-title text-2xl font-bold">${event.name}</h1>
      </header>
      <div class="event-card">
        <div class="img-event-container">
          <img src="${event.img}" alt="${event.name}" class="event-image">
        </div>
        <div class="event-card-details">
            <div class="left-event-details">
                <p class="description text-gray-700">${event.description}</p>
                <p class="location">${event.venue.location}</p>
                <p class="availableTickets" id="availableTickets">Tickets left: ${event.availableTickets}</p>
            </div>
            <div class="right-event-details">
                <p class="from-date">${startDate} / ${startHour}</p>
                <p class="end-date">${endDate} / ${endHour}</p>
            </div>
        </div>
      </div>
    `;
  }