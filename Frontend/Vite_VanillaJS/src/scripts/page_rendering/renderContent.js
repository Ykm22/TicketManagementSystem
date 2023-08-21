import { renderHomePage } from './renderHomePage.js'
import { renderOrdersPage } from './renderOrdersPage.js'
import { renderLoginPage } from './renderLoginPage.js'
import { renderRegisterPage } from './renderRegisterPage.js'
import { renderAnalyticsPage } from './renderAnalyticsPage.js'

export function renderContent(url, api_urls) {
    console.log(url);
    const mainContentDiv = document.querySelector('.main-content-component');
    mainContentDiv.innerHTML = '';

    if(url === "/" || url === "/login"){
      renderLoginPage(api_urls);
    } else if(url === '/register'){
      renderRegisterPage(api_urls);
    } else if (url === '/events') {
      console.log("rendering home page!!!!");
      renderHomePage(api_urls);
    } else if (url === '/orders') {
      renderOrdersPage(api_urls);
    } else if (url === '/analytics'){
      renderAnalyticsPage(api_urls);
    }
  }