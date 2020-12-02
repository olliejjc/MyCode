var revenueGlobalTotal2022 = 0;
var revenueGlobalTotal2021 = 0;
var heirarchalObjectList = [];
var heirarchyKey = "orderedByRegion";
var expandedTree = false;

$( document ).ready(function() {

    $.ajax({
        type: 'GET',
        url: 'data.json', 
        contentType: "application/json",
        dataType: 'json',
        success: function(jsonResult){
            buildHeirarchalObjectList(jsonResult);
            nestedObjectTree = generateNestedObjectTree();
            buildFinancialReportTree(nestedObjectTree, document.getElementById('testUL'));
            buildGlobalElementData();
            createClickableCaretElements();             
        }

    })
});

//Generate list of objects depending on their ordering to be sorted in a heirarchy
function buildHeirarchalObjectList(jsonResult){
    for (var company in jsonResult) {
        if (jsonResult.hasOwnProperty(company)) {
            elements = jsonResult[company];
            for(i = 0; i < elements.length; i++){
                region = getRegion(elements[i]);
                country = getCountry(elements[i]);
                revenue = getRevenue(elements[i]);
                productLine = getProductLine(elements[i]);
                if(heirarchyKey == "orderedByRegion"){
                    heirarchalObjectList.push([{region: region}, {country: country}, {company: company}, {productLine:productLine}, revenue]);
                }
                else{
                    heirarchalObjectList.push([{productLine: productLine}, {company: company}, {region: region}, {country:country}, revenue]);
                }
            }
        }
    }
}

//Sorts the lists of objects into a nested tree heirarchy
function generateNestedObjectTree(){
    nestedObjectTree = [],
    temp = { _: nestedObjectTree };
    heirarchalObjectList.forEach(function(heirarchalObject){
        heirarchalObject.reduce(function (level, keyValuePair) {
        key = "";
        value = "";
        //gets the object type i.e region, country..
        for(var keyType in keyValuePair){
            key = keyType;
            if(isNaN(key)){
                value = keyValuePair[keyType];
            } 
            else{
                value = keyValuePair;
                break;
            }
        }
        if (!level[value]) {
            //revenue is an object containing 3 years of revenue data so handled differently
            if(typeof value === 'object'){
                level[value] = { _: [] };
                level._.push({ revenue: value });
            }
            else{
                level[value] = { _: [] };
                heirarchObject = {};
                heirarchObject[key] = value; 
                heirarchObject["children"] = level[value]._ ;
                level._.push(heirarchObject);
            }
        }
        return level[value];
        }, temp);
    });
    return nestedObjectTree;
}

//build the display for the nested object tree
function buildFinancialReportTree(nestedObjectTree, container) {
    for (var i = 0; i < nestedObjectTree.length; i++) {
        if(nestedObjectTree[i].children !== undefined){
            var li = document.createElement('li');
            if(nestedObjectTree[i].region !== undefined){
                var revenueTotalsForRegion = calculateRevenueForEachElement(nestedObjectTree[i], "revenue", revenueTracker = [0,0], 0);
                revenueGlobalTotal2022 += revenueTotalsForRegion[0];
                revenueGlobalTotal2021 += revenueTotalsForRegion[1];
                var revenueTotalRegion2022 = cleanRevenueValue(revenueTotalsForRegion);
                var revenuePercentageGrowthRegion = getRevenuePercentageGrowth(revenueTotalsForRegion);
                var caretExists = "";
                if(expandedTree === false){
                    caretExists = "caret ";
                }
                li.innerHTML = generateListElementData(caretExists, nestedObjectTree[i].region, revenueTotalRegion2022, revenuePercentageGrowthRegion);        
            }
            if(nestedObjectTree[i].country !== undefined){
                var revenueTotalsForCountry= calculateRevenueForEachElement(nestedObjectTree[i], "revenue", revenueTracker = [0,0], 0);
                var revenueTotalCountry2022 = cleanRevenueValue(revenueTotalsForCountry);
                var revenuePercentageGrowthCountry = getRevenuePercentageGrowth(revenueTotalsForCountry);
                var caretExists = "";
                if(heirarchyKey === "orderedByRegion" && expandedTree === false){
                    caretExists = "caret ";
                }
                li.innerHTML = generateListElementData(caretExists, nestedObjectTree[i].country, revenueTotalCountry2022, revenuePercentageGrowthCountry);
            }
            if(nestedObjectTree[i].company !== undefined){
                var revenueTotalsForCompany= calculateRevenueForEachElement(nestedObjectTree[i], "revenue", revenueTracker = [0,0], 0);
                var revenueTotalCompany2022 = cleanRevenueValue(revenueTotalsForCompany);
                var revenuePercentageGrowthCompany = getRevenuePercentageGrowth(revenueTotalsForCompany);
                var caretExists = "";
                if(expandedTree === false){
                    caretExists = "caret ";
                }
                li.innerHTML = generateListElementData(caretExists, nestedObjectTree[i].company, revenueTotalCompany2022, revenuePercentageGrowthCompany);
            }
            if(nestedObjectTree[i].productLine !== undefined){
                var revenueTotalsForProductLine= calculateRevenueForEachElement(nestedObjectTree[i], "revenue", revenueTracker = [0,0], 0);
                var revenueTotalProductLine2022 = cleanRevenueValue(revenueTotalsForProductLine);
                var revenuePercentageGrowthProductLine = getRevenuePercentageGrowth(revenueTotalsForProductLine);
                var caretExists = "";
                if(heirarchyKey === "orderedByProductLine" && expandedTree === false){
                    caretExists = "caret ";
                }
                li.innerHTML = generateListElementData(caretExists, nestedObjectTree[i].productLine, revenueTotalProductLine2022, revenuePercentageGrowthProductLine);

            }
            /*if the current level of the nestedObjectTree has children use recursion to go down to the next level, when there 
              are no children at the current level you've reached the bottom of the object tree*/
            if (nestedObjectTree[i].children.length > 0) {
                var ul = document.createElement('ul');
                if(expandedTree === false){
                    ul.className = "nested";
                }
                else{
                    ul.className = "nested active";
                }
                li.appendChild(ul);
                buildFinancialReportTree(nestedObjectTree[i].children, ul);
            }
            container.appendChild(li);
        }
    }
}

//adds data to the global display key
function buildGlobalElementData(){
    revenueGlobalTotalCleaned2022 = cleanRevenueValue([revenueGlobalTotal2022, revenueGlobalTotal2021]);
    var revenuePercentageGrowthGlobal = getRevenuePercentageGrowth([revenueGlobalTotal2022, revenueGlobalTotal2021]);
    var percentageChangeElement;
    if (revenuePercentageGrowthGlobal > 0) {
        percentageChangeElement = "<span id=\"percentageRevenueChangeGlobalPositive\">&uarr;" + revenuePercentageGrowthGlobal + "%</span>"
    }
    else{
        percentageChangeElement = "<span id=\"percentageRevenueChangeGlobalNegative\">&darr;" + revenuePercentageGrowthGlobal + "%</span>"
    }
    var caretExists = "";
    if(expandedTree === false){
        caretExists = "caret ";
    }
    var node =  "<span class=\"" + caretExists + "\" id=\"globalSpan\">Global</span>" +
                "<span class=\"revenue2022\">&#8369; " + revenueGlobalTotalCleaned2022 + "B</span>" +
                percentageChangeElement;
                
    document.getElementById("Global").insertAdjacentHTML('afterbegin', node );
}

//makes the Caret Elements clickable so you can click up and down through the tree
function createClickableCaretElements(){
    var toggler = document.getElementsByClassName("caret");
    var i;
    for (i = 0; i < toggler.length; i++) {
        toggler[i].addEventListener("click", function() {
            if(this.parentElement.parentElement.className == "nested active"){
                    $(this).closest('li').siblings().toggle();
            }
        
            this.parentElement.querySelector(".nested").classList.toggle("active");
            this.classList.toggle("caret-down");
        });
    }
}

//expand or collapse the tree display
function expandOrCollapseAll(){
    var expandAllButton = document.getElementById("expandAllButton");
    revenueGlobalTotal2022 = 0;
    revenueGlobalTotal2021 = 0;
    if(expandAllButton.value == "Expand All"){
        expandedTree = true;
        resetTreeList();
        nestedObjectTree = generateNestedObjectTree();
        buildFinancialReportTree(nestedObjectTree, document.getElementById('testUL'));
        buildGlobalElementData();
        expandAllButton.value = "Collapse All";
    }
    else{
        expandedTree = false;
        resetTreeList()
        nestedObjectTree = generateNestedObjectTree();
        buildFinancialReportTree(nestedObjectTree, document.getElementById('testUL'));
        buildGlobalElementData();
        createClickableCaretElements();
        expandAllButton.value = "Expand All";
    }
    updateActiveRevenueDisplay();
}

//toggle the order grouping between product line and region
function orderBySelectedGrouping(){
    $.ajax({
            type: 'GET',
            url: 'data.json', 
            contentType: "application/json",
            dataType: 'json',
            success: function(result){
            revenueGlobalTotal2022 = 0;
            revenueGlobalTotal2021 = 0;
            heirarchalObjectList = [];
            var switchGroupingButton = document.getElementById("switchGroupingButton");
            if (switchGroupingButton.value=="Order By Product Line") {
                switchGroupingButton.value = "Order By Region";
            }
            else {
                switchGroupingButton.value = "Order By Product Line"
            }
            resetTreeList();

            if(heirarchyKey == "orderedByRegion"){
                heirarchyKey = "orderedByProductLine";
            }
            else{
                heirarchyKey = "orderedByRegion";
            }
            buildHeirarchalObjectList(result);
            nestedObjectTree = generateNestedObjectTree();
            buildFinancialReportTree(nestedObjectTree, document.getElementById('testUL'));
            buildGlobalElementData();
            createClickableCaretElements();
            updateActiveRevenueDisplay();
        }
    });
}

//initialises the data that goes into a list element
function generateListElementData(caretExists, key, revenueTotal2022, percentageGrowthInAYear){
    var percentageRevenueChange;
    if (percentageGrowthInAYear > 0) {
        percentageRevenueChange = "percentageRevenueChangePositive";
    }
    else{
        percentageRevenueChange = "percentageRevenueChangeNegative";
    }
    var listElementData = "<span class=\"" + caretExists + "key " + percentageRevenueChange +"\">" + key + "</span>" + 
                                "<span class=\"" + percentageRevenueChange + " revenue2022\">&#8369; " + revenueTotal2022 + "B</span>" +
                                "<span class=\"" + percentageRevenueChange + " revenueGrowth\">&uarr;" + percentageGrowthInAYear + "%</span>";
    return listElementData;
}

//resets the tree list back to its original form
function resetTreeList(){
    document.getElementById("myUL").innerHTML = "";
    var rootList = document.getElementById("myUL");
    var li = document.createElement('li');
    li.setAttribute("id", "Global");
    rootList.appendChild(li);
    var globalList = document.getElementById("Global");
    var ul = document.createElement('ul');
    ul.setAttribute("id", "testUL");
    if(expandedTree == false){
        ul.className = "nested";
    }
    else{
        ul.className = "nested active";
    }
    li.appendChild(ul);
}

// toggles between showing items with positive revenue
function togglePositiveRevenueDisplay(){ 
    var togglePositiveRevenueButton = document.getElementById("togglePositiveRevenue");
    if(togglePositiveRevenueButton.value == "Hide Positive Revenue"){
        togglePositiveRevenueButton.value = "Show Positive Revenue";
    }
    else{
        togglePositiveRevenueButton.value = "Hide Positive Revenue";
    }

    var listOfPositiveRevenueElements = document.getElementsByClassName("percentageRevenueChangePositive");
    var i;

    for (i = 0; i < listOfPositiveRevenueElements.length; i++) {
        if(listOfPositiveRevenueElements[i].style.display === "none") {
            listOfPositiveRevenueElements[i].style.display = "inline-block";
        } 
        else {
            listOfPositiveRevenueElements[i].style.display = "none";
        }
    }
}

//updates positive revenue elements display
function updateActiveRevenueDisplay(){
    var togglePositiveRevenueButton = document.getElementById("togglePositiveRevenue");
    var listOfPositiveRevenueElements = document.getElementsByClassName("percentageRevenueChangePositive");
    var i;
    for (i = 0; i < listOfPositiveRevenueElements.length; i++) {
        if(togglePositiveRevenueButton.value == "Hide Positive Revenue"){
            listOfPositiveRevenueElements[i].style.display = "inline-block";
        }
        else{
            listOfPositiveRevenueElements[i].style.display = "none";
        }        
    }
}

//cleans up the revenue value for the year 2022
function cleanRevenueValue(revenueTotalsForRegions){
    return (revenueTotalsForRegions[0] / 100).toFixed(2);
}

//gets the revenue growth between 2021 and 2022
function getRevenuePercentageGrowth(revenueTotalsForRegions){
    var revenueTotal2022 = revenueTotalsForRegions[0];
    var revenueTotal2021 = revenueTotalsForRegions[1];
    var growthBetweenYears = revenueTotal2022 - revenueTotal2021;
    return (Math.round((growthBetweenYears / revenueTotal2022) * 100 * 100) / 100).toFixed(2);
}

//calculates total revenue for each element checked and all its children
function calculateRevenueForEachElement(element, label, revenueTracker, recursiveCallTracker) {
    //tracks each recursive call
    recursiveCallTracker++;
    for(var key in element){
        //if we have reached the bottom of the element tree we will be at the revenue level
        if(key === label) { 
            return element; 
        }
    }

    if(element.hasOwnProperty("children")){
        element.children.forEach(function (element) {
            var foundLabel = calculateRevenueForEachElement(element, label, revenueTracker, recursiveCallTracker);
            if(foundLabel) { 
                revenue2022Total = foundLabel.revenue[2022];
                revenue2021Total = foundLabel.revenue[2021];
                revenueTracker[0] +=  revenue2022Total;
                revenueTracker[1] +=  revenue2021Total;
                return foundLabel;
            }
        });
    
    }
    
    recursiveCallTracker--;
    /*When the method is finished and the last recursive call has been made return the total revenue for the element and 
      all its child elements*/
    if(recursiveCallTracker == 0){
        return revenueTracker;
    }
    else{
        return null;
    }
}

function getRegion(elements){
    return elements['geography'][0];
}

function getCountry(elements){
    return elements['geography'][1];
}

function getRevenue(elements){
    return elements['revenue'];
}

function getProductLine(elements){
    return elements['productLine'];
}