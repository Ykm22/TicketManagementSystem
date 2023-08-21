export function getAnalyticsPageTemplate(){
    return `
        <div class="analytics-container">
        <div class="analytics-box">
            <div class="logout-button">
                Log out
            </div>
                <div class="background-image"></div> <!-- New div for background image -->
                <div class="analytics-content">
                    <div class="year-winnings">
                        <div class="year-options">
                            <div class="year-inputs">
                            </div>
                            <div class="generate-winnings-button">
                                Generate
                            </div>    
                            <div class="generate-winnings-predictions-button">
                                Predict for current year
                            </div>    
                        </div>
                        <div class="winnings-plot">
                            <img id="winnings-plot" src="src/assets/initial_graph.png" alt="plot winnings">
                        </div>
                    </div>
                    <div class="ages-event-types">
                        <div class="age-options">
                            <select id="event-type-select">
                            </select>
                            <div class="generate-age-percentages-button">
                                Generate
                            </div>
                            <div class="predict-age-percentages-button">
                                Predict
                            </div>
                        </div>
                        <div class="percentages-results">
                            <div class="percentages-title">
                                Grouped age percentages
                            </div>
                            <div class="actual-percentages">
                                <div class="male">
                                    <span class="male_span">M</span>
                                    <ul class="age-groups">
                                    </ul>
                                </div>
                                <div class="female">
                                    <span class="female_span">F</span>
                                    <ul class="age-groups">
                                    </ul>
                                </div>
                            </div>
                            <div class="predicted-percentages">
                                <div class="male">
                                    <span class="male_span">M</span>
                                    <ul class="age-groups">
                                    </ul>
                                </div>
                                <div class="female">
                                    <span class="female_span">F</span>
                                    <ul class="age-groups">
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="percentages-details">
                            <div class="groups-title">
                                Groups
                            </div>
                            <div class="groups-sub-title">
                                <div class="age-label-details">
                                    <div class="sub-title">Ages</div>
                                    <div class="age-label-container">
                                        <div class="age-label">5 - 17</div>
                                        <div class="age-label">18 - 25</div>
                                        <div class="age-label">25 - 40</div>
                                        <div class="age-label">40+</div>
                                    </div>
                                </div>
                                <div class="male-details">
                                    <div class="sub-title">Male</div>
                                    <div class="group-container">
                                    </div>
                                </div>
                                <div class="female-details">
                                    <div class="sub-title">Female</div>
                                    <div class="group-container">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;
}