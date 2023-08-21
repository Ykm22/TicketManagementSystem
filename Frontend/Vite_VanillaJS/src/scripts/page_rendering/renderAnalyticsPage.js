import { getAnalyticsPageTemplate } from '../templates/getAnalyticsPageTemplate.js'
import { createLoader, deleteLoader } from './loader.js';
import { fetchEventYears } from '../network/getEventYears.js'
import { fetchEventTypes } from '../network/getEventTypes.js'
import { fetchWinningsPlot } from '../network/getWinningsPlot.js'
import { fetchPredictedWinningsPlot } from '../network/getPredictedWinningsPlot.js'
import { fetchEventPercentages } from '../network/getEventPercentages.js'
import { handleLogout } from '../handlers/handleLogout.js';

export function renderAnalyticsPage(api_urls){
    createLoader();
    const mainContentDiv = document.querySelector('.main-content-component');
    mainContentDiv.innerHTML = getAnalyticsPageTemplate();
    
    createEventYears(api_urls);
    createGenerateWinningsButton(api_urls);
    createPredictWinningsButton(api_urls);
    
    hidePercentagesTitle();
    populateEventTypesSelect(api_urls);
    createEventTypeSelectChange();
    createGeneratePercentagesButton(api_urls);
    createLogout(api_urls);
    deleteLoader();
    // createRegisterHandler(api_urls);
}

function createLogout(api_urls){
    const button = document.querySelector(".logout-button");
    button.addEventListener("click", () => {
        handleLogout(api_urls);
    });
}

function createEventTypeSelectChange(){
    const selectEventTypes = document.querySelector("#event-type-select");
    selectEventTypes.addEventListener("change", () => {
        hidePercentagesTitle();
    });
}

function hidePercentagesTitle(){
    const div = document.querySelector(".percentages-title");
    div.style.display = "none";
    const divGroupsTitle = document.querySelector(".groups-title");
    divGroupsTitle.style.display = "none";
    const divSubGroupsTitle = document.querySelector(".groups-sub-title");
    divSubGroupsTitle.style.display = "none";
    const divActualPercentages = document.querySelector(".actual-percentages");
    divActualPercentages.style.display = "none";
    const divPredictedPercentages = document.querySelector(".predicted-percentages");
    divPredictedPercentages.style.display = "none";
}

function showPercentagesTitle(){
    const div = document.querySelector(".percentages-title");
    div.style.display = "block";
    const divGroupsTitle = document.querySelector(".groups-title");
    divGroupsTitle.style.display = "block";
    const divSubGroupsTitle = document.querySelector(".groups-sub-title");
    divSubGroupsTitle.style.display = "flex";
    const divActualPercentages = document.querySelector(".actual-percentages");
    divActualPercentages.style.display = "block";
    // const divPredictedPercentages = document.querySelector(".predicted-percentages");
    // divPredictedPercentages.style.display = "block";
}

function createGeneratePercentagesButton(api_urls){
    const button = document.querySelector(".generate-age-percentages-button");
    button.addEventListener("click", () => {
        const selectEventTypesValue = document.querySelector("#event-type-select").value;
        fetchEventPercentages(api_urls.flask, selectEventTypesValue)
            .then(data => {
                const male_ages = data.m;
                const female_ages = data.f;
                createActualPercentagesLists(male_ages, female_ages);
                showPercentagesTitle();
            })
            .catch(error => {
                toastr.error(error);
            })
    });
}

function createActualPercentagesLists(male_ages, female_ages){
    const maleList = document.querySelector(".actual-percentages .male ul");
    createPercentagesList(maleList, male_ages, 'm');
    const femaleList = document.querySelector(".actual-percentages .female ul");
    createPercentagesList(femaleList, female_ages, 'f');
}

function createPercentagesList(list, ages, sex){
    const colors = {
        1: "#008C40",
        2: "#FFA62E",
        3: "#3B82F6",
        4: "#FF5B4C",
    };
    list.innerHTML = '';
    let divGroupContainer = null;
    if(sex === 'f'){
        divGroupContainer = document.querySelector(".male-details .group-container");
    } else {
        divGroupContainer = document.querySelector(".female-details .group-container");
    }
    divGroupContainer.innerHTML = '';
    for(const age_group in ages){ // parsing group ages
        const percentage = ages[age_group]; // percentages for this group age 
        const listItem = document.createElement("li");
        listItem.classList.add("age-group");
        const divText = document.createElement("div");
        divText.classList.add("percentage");
        divText.style.width = `calc(${percentage} * 400px)`;
        divText.style.backgroundColor = colors[age_group];
        listItem.appendChild(divText);
        list.appendChild(listItem);
        
        const divGroup = document.createElement("div");
        divGroup.classList.add("group");
        divGroup.style.backgroundColor = colors[age_group];
        
        const groupName = document.createElement("span");
        groupName.classList.add("group-name");
        groupName.textContent = `${age_group}: `;
        
        const probability = document.createElement("span");
        probability.classList.add("probability");
        probability.textContent = `${(ages[age_group] * 100).toFixed(2)}%`;

        // divGroup.appendChild(groupName);
        divGroup.appendChild(probability);

        divGroupContainer.appendChild(divGroup);
    }
    
}

function populateEventTypesSelect(api_urls){
    fetchEventTypes(api_urls.flask)
        .then(data => {
            const event_types = data.event_types;
            const selectEventTypes = document.querySelector("#event-type-select");
            for(let i = 0; i < event_types.length; i++){
                createOption(selectEventTypes, event_types[i]);
            }
        })
        .catch(error => {
            toastr.error(error);
        });
}

function createOption(select, event_type){
    const option = document.createElement('option');
    option.value = event_type;
    option.textContent = event_type;
    select.appendChild(option);
}

function createPredictWinningsButton(api_urls){
    const button = document.querySelector(".generate-winnings-predictions-button");
    button.addEventListener("click", () => {
        fetchPredictedWinningsPlot(api_urls.flask)
            .then(plotImageUrl => {
                const img = document.querySelector("#winnings-plot");
                img.src = plotImageUrl;
            })
            .catch(error => {
                toastr.error(error);
            });
    });
}

function createEventYears(api_urls){
    fetchEventYears(api_urls.flask)
        .then(data => {
            const yearInputs = document.querySelector(".year-inputs");
            const years = data.years;
            years.sort(function(a, b){
                return parseInt(a) - parseInt(b);
            });
            for(let i = 0; i < years.length; i++){
                const year = years[i];
                const label = document.createElement("label");
                const input = document.createElement("input");
                input.type = "checkbox";
                input.value = year;
                const textNode = document.createTextNode(` ${year}`);
                label.appendChild(input);
                label.appendChild(textNode);
                yearInputs.appendChild(label);
            }
        })
        .catch(error => {
            toastr.error(error);
        });
}

function createGenerateWinningsButton(api_urls){
    const button = document.querySelector(".generate-winnings-button");
    button.addEventListener("click", () => {
        const years = getSelectedYears();      
        if(years.length == 0){
            toastr.info("cmon man choose some years");
            return;
        }  
        fetchWinningsPlot(api_urls.flask, years)
            .then(plotImageUrl => {
                const img = document.querySelector("#winnings-plot");
                img.src = plotImageUrl;
            })
            .catch(error => {
                toastr.error(error);
            });
    });
}

function getSelectedYears(){
    const checkedCheckboxes = document.querySelectorAll('.year-inputs input[type="checkbox"]:checked');
    const checkedValues = [];

    for(let i = 0; i < checkedCheckboxes.length; i++){
        checkedValues.push(checkedCheckboxes[i].value);
    }
    return checkedValues;
}