$(document).ready(function () {

let searchContainer = document.getElementById("search-container");
searchContainer.style.backgroundColor = "#F5F5F5";
searchContainer.style.border = "1px solid black";
searchContainer.style.borderBottom = "1.25px solid #F5F5F5";
searchContainer.style.transitionDuration = "unset";
});

let informationFilterFlag = false;
function toogleInformationFilterButton() {
    let filterButton = document.getElementById("information-filter-box");
    let filterButtonActive = document.getElementById("information-filter-box-active");
    let filterSection = document.getElementById("information-content-section");
    if(informationFilterFlag) {
        filterButton.style.display = "none";
        filterButtonActive.style.display = "flex";
        filterSection.style.display = "block";
        informationFilterFlag = false;
    } else {
        filterButton.style.display = "flex";
        filterButtonActive.style.display = "none";
        filterSection.style.display = "none";
        informationFilterFlag = true;
    }
}

let functionFilterFlag = false;
function toogleFunctionFilterButton() {
    let filterButton = document.getElementById("function-filter-box");
    let filterButtonActive = document.getElementById("function-filter-box-active");
    let filterSection = document.getElementById("function-content-section");
    if(functionFilterFlag) {
        filterButton.style.display = "none";
        filterButtonActive.style.display = "flex";
        filterSection.style.display = "block";
        functionFilterFlag = false;
    } else {
        filterButton.style.display = "flex";
        filterButtonActive.style.display = "none";
        filterSection.style.display = "none";
        functionFilterFlag = true;
    }
}

let impactFilterFlag = false;
function toogleImpactFilterButton() {
    let filterButton = document.getElementById("impact-filter-box");
    let filterButtonActive = document.getElementById("impact-filter-box-active");
    let filterSection = document.getElementById("impact-content-section");
    if(impactFilterFlag) {
        filterButton.style.display = "none";
        filterButtonActive.style.display = "flex";
        filterSection.style.display = "block";
        impactFilterFlag = false;
    } else {
        filterButton.style.display = "flex";
        filterButtonActive.style.display = "none";
        filterSection.style.display = "none";
        impactFilterFlag = true;
    }
}