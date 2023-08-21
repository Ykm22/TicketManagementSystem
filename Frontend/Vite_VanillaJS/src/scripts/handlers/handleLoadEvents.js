import { fetchEventsPaged } from "../network/getEventsPaged";
import { deleteLoader } from "../page_rendering/loader";
import { createFilterButton, createFooterInteractables, createNameFilterInput, createRefreshButton, populateEventsContainer, populateSelectFilterOptions, updateCurrentPageSelect } from "../page_rendering/renderHomePage";

export function handleLoadEvents(api_urls, PAGE, PAGE_SIZE){
    fetchEventsPaged(api_urls.spring, PAGE, PAGE_SIZE)
      .then((data) => {
        toastr.success("Events loaded successfully! 😁");
        sessionStorage.setItem("events", JSON.stringify(data.events));
        populateEventsContainer(data.events, api_urls);
        createNameFilterInput(api_urls);
        createFilterButton(api_urls);
        createRefreshButton(api_urls);
        populateSelectFilterOptions();
        updateCurrentPageSelect(PAGE, data.totalPages);
      })
      .catch(error => {
        toastr.error(error);
      })
      .finally(() =>
        deleteLoader()
      );
}