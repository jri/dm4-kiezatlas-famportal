/*
    View Model
    ==========

    detailGeoObject    - 

    Left Column
    -----------

    famportalTree      - Famportal category tree (topic of type "famportal.category" with all child topics)
        .count {       - For each category: the count of assigned Geo Objects
            "cat-<id>": <count>
        }

    famportalCategory  - Selected Famportal category (topic of type "famportal.category")
        topic {
        }

    Middle Column
    -------------

    assignedObjects    - Geo Objects assigned to selected Famportal category (middle box)
        [
            topic {
            }.selected (boolean)
        ].selectedCount

    Right Column
    ------------

    searchTerm         - The term entered in the search field (right box)

    geoObjects         - Found Geo Objects (by name)
        [
            topic {
            }.selected (boolean)
        ].selectedCount

    searchResult       - Found Geo Objects (by category), grouped by 1) KA criteria and 2) KA category
        criteriaResults [{
            criteria: {
            }
            categories: [{
                category: {
                }
                geo_objects: [{
                }]
                expanded: boolean
                selected: boolean
            }]
        }].categoryCount
          .selectedCount
*/
angular.module("famportal", ["ngRoute"]).config(function($routeProvider) {
    console.log("Configuring routes")
    $routeProvider
        .when("/editorial", {
            templateUrl: "/de.kiezatlas.famportal/partials/editorial.html",  controller: "editorialController"
        })
        .when("/categories", {
            templateUrl: "/de.kiezatlas.famportal/partials/categories.html", controller: "categoriesController"
        })
        .otherwise({redirectTo: "/editorial"})
})
.config(function($httpProvider) {
    console.log("Configuring request/response interceptors")
    $httpProvider.interceptors.push(function($injector) {
        var $http
        return {
            request: function(config) {
                // console.log("Loading starts")
                showLoadingWidget()
                return config
            },
            response: function(response) {
                $http = $http || $injector.get("$http")     // getting $http lazily avoids circular dependency
                if (!$http.pendingRequests.length) {
                    // console.log("Loading complete")
                    hideLoadingWidget()
                }
                return response
            }
        }

        function showLoadingWidget() {
            setLoadingWidgetDisplay("block")
        }

        function hideLoadingWidget() {
            setLoadingWidgetDisplay("none")
        }

        function setLoadingWidgetDisplay(display) {
            document.getElementById("loading-widget").style.display = display
        }
    })
})
