import '../styles/style.css'
import { renderContent } from './page_rendering/renderContent.js'

function setupInitialPage(api_urls){
  const initialUrl = window.location.pathname;
  sessionStorage.setItem("whereFrom", "from setupInitialPage in main.js");
  renderContent(initialUrl, api_urls);
}

function setupNavBar(api_urls){
  const navLinks = document.querySelectorAll('nav a');
  console.log(navLinks);
  navLinks.forEach((link) => {
    const isEventListenerAdded = link.__eventListenerAdded;
    if(!isEventListenerAdded){
      link.__eventListenerAdded = true;
      link.addEventListener('click', (event) => {
        event.preventDefault();
        const href = link.getAttribute('href');
        sessionStorage.setItem("whereFrom", "from setupNavBar");
        navigateTo(href, api_urls);
      });
    }
  })
}

export function navigateTo(url, api_urls){
  history.pushState(null, null, url);
  sessionStorage.setItem("whereFrom", "from navigateTo function definition in main.js");
  renderContent(url, api_urls);
}

let api_urls = {
  "spring" : "http://172.16.98.34:8080/tms/api/spring/",
  "net" : "http://172.16.98.34:9090/tms/api/net/",
  "flask": "http://172.16.98.34:7070/"
};
// !ON CTRL+S, RELOADS THIS FILE => setupNavBar() gets RECALLED => could re-assign onclick handlers X) 
setupNavBar(api_urls);
setupInitialPage(api_urls);