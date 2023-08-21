export function getHomePageTemplate() {
    return `
     <div id="content" >
        <img src="./src/assets/Endava.png" alt="summer">
        <div class="orders-filter-options">
            <div class="filter-inputs">
              <input id="inputNameFilter" type="text" placeholder="Name...">
              <select id="selectLocationFilterInput">
                <option value="" disabled selected>Location</option>
              </select>
              <select id="selectEventTypeFilterInput">
                <option value="" disabled selected>Event type</option>
              </select>
            </div>
            <div class="filter-button">
              <button>Find</button>
            </div>
            <div class="refresh-button">
              <button>Refresh</button>
            </div>
          </div>
        <div class="events flex items-center justify-center flex-wrap">
        </div>
      </div>
      <footer id="home-footer">
        <div class="button" id="button-prev">
          Prev
        </div>
        <select id="select-current-page">
          <option value="1">1</option>
        </select>
        <div class="button" id="button-next">
          Next
        </div>
      </footer>
    `;
  }