/*
    Application model

    famportalTree      - Famportal category tree (topic of type "famportal.category" with all child topics)

    famportalCategory  - Selected Famportal category (topic of type "famportal.category")
        topic {
        }

    geoObjects         - Geo Objects assigned to selected Famportal category (middle box)
        [
            topic {
            }.selected (boolean)
        ].selectedCount

    searchTerm         - The term entered in the search field (right box)

    searchResult       - Found Geo Objects, grouped by 1) KA criteria and 2) KA category
        criteriaResults [{
            criteria: {
            }
            categories: [{
                category: {
                }
                geo_objects: [{
                }]
                expanded: boolean
                include: boolean
            }]
        }].stats {
        }
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
