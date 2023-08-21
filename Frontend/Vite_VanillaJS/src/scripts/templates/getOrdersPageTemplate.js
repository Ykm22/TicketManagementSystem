export function getOrdersPageTemplate() {
  return `
    <div id="content">
      <h1 class="text-2xl mb-4 mt-8 text-center">Purchased Tickets</h1>
      <div class="orders-options">
        <div class="orders-sort-options">
          <div class="sort-radio-inputs">
            <div class="sort-criteria-options">
              <label>
                <input type="radio" name="sortCriteria" value="price">Price
              </label>
              <label>
                <input type="radio" name="sortCriteria" value="name">Name
              </label>
            </div>
            <div class="sort-order-options">
              <label>
                <input type="radio" name="sortOrder" value="ascending">Ascending
              </label>
              <label>
                <input type="radio" name="sortOrder" value="descending">Descending
              </label>
            </div>
          </div>
          <div class="sort-button">
            <button>Sort</button>
          </div>
        </div>
      </div>
      <div class="orders">
      </div>
  `;
}