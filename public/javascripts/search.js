$(document).ready(function () {

let searchContainer = document.getElementById("search-container");
searchContainer.style.backgroundColor = "#F5F5F5";
searchContainer.style.border = "1px solid black";
searchContainer.style.borderBottom = "1.25px solid #F5F5F5";
searchContainer.style.transitionDuration = "unset";
});

let informationFilterFlag = true;
function disableInformationFilterButton() {
    let filterButton = document.getElementById("information-filter-box");
    let filterButtonLabel = document.getElementById("information-filter-box-label");
    if(informationFilterFlag) {
        filterButton.style.backgroundColor = "lightgray";
        filterButtonLabel.style.color = "#4F4F4F";
        informationFilterFlag = false;
    } else {
        filterButton.style.backgroundColor = "white";
        filterButtonLabel.style.color = "black";
        informationFilterFlag = true;
    }
}

let functionFilterFlag = true;
function disableFunctionFilterButton() {
    let filterButton = document.getElementById("function-filter-box");
    let filterButtonLabel = document.getElementById("function-filter-box-label");
    if(functionFilterFlag) {
        filterButton.style.backgroundColor = "lightgray";
        filterButtonLabel.style.color = "#4F4F4F";
        functionFilterFlag = false;
    } else {
        filterButton.style.backgroundColor = "white";
        filterButtonLabel.style.color = "black";
        functionFilterFlag = true;
    }
}

let impactFilterFlag = true;
function disableImpactFilterButton() {
    let filterButton = document.getElementById("impact-filter-box");
    let filterButtonLabel = document.getElementById("impact-filter-box-label");
    if(impactFilterFlag) {
        filterButton.style.backgroundColor = "lightgray";
        filterButtonLabel.style.color = "#4F4F4F";
        impactFilterFlag = false;
    } else {
        filterButton.style.backgroundColor = "white";
        filterButtonLabel.style.color = "black";
        impactFilterFlag = true;
    }
}