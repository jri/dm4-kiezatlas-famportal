/*
    Application model

    root               - Famportal category tree (topic of type "famportal.category" with all child topics)

    famportalCategory  - Selected Famportal category (topic of type "famportal.category")
        topic {
        }

    geoObjects         - Geo Objects assigned to selected Famportal category (middle box)
        [
            topic {
            }.selected (boolean)
        ].selectedCount

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
angular.module("famportal", ["ngRoute"])
    .config(function($routeProvider) {
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
                document.getElementById("loading-widget").style.display = "block"
            }

            function hideLoadingWidget() {
                document.getElementById("loading-widget").style.display = "none"
            }
        })
    })
    .controller("editorialController", function($scope, famportalService) {

        famportalService.getFamportalCategories(function(categories) {
            $scope.root = categories
        })

        $scope.selectFamportalCategory = function(category) {
            $scope.famportalCategory = category
            updateGeoObjects()
        }

        $scope.selectGeoObject = function() {
            $scope.geoObjects.selectedCount = selectedIds($scope.geoObjects).length
        }

        $scope.selectKiezatlasCategory = function() {
            updateStats($scope.searchResult)
        }

        $scope.searchGeoObjects = function() {
            famportalService.searchGeoObjects($scope.searchTerm, function(searchResult) {
                $scope.searchResult = searchResult.search_result
                initSearchResult($scope.searchResult)
                updateStats($scope.searchResult)
            })
        }

        $scope.createAssignments = function() {
            famportalService.createAssignments($scope.famportalCategory.id, categoryIds($scope.searchResult),
                updateGeoObjects)
        }

        $scope.deleteAssignments = function() {
            famportalService.deleteAssignments($scope.famportalCategory.id, selectedIds($scope.geoObjects),
                updateGeoObjects)
        }

        // ---

        function updateGeoObjects() {
            famportalService.getGeoObjectsByCategory($scope.famportalCategory.id, function(geoObjects) {
                $scope.geoObjects = geoObjects
                $scope.geoObjects.selectedCount = 0
            })
        }

        function selectedIds(geoObjects) {
            var selected = []
            angular.forEach(geoObjects, function(geoObject) {
                if (geoObject.selected) {
                    selected.push(geoObject.id)
                }
            })
            return selected
        }

        function initSearchResult(searchResult) {
            angular.forEach(searchResult, function(criteriaResult) {
                angular.forEach(criteriaResult.categories, function(categoryResult) {
                    if (categoryResult.geo_objects.length) {
                        categoryResult.include = true
                    }
                })
            })
            //
            searchResult.stats = {}
        }

        function updateStats(searchResult) {
            var criteriaCount  = searchResult.length
            var categoryCount  = 0
            var geoObjectCount = 0
            angular.forEach(searchResult, function(criteriaResult) {
                categoryCount += criteriaResult.categories.length
                angular.forEach(criteriaResult.categories, function(categoryResult) {
                    if (categoryResult.include) {
                        geoObjectCount += categoryResult.geo_objects.length
                    }
                })
            })
            searchResult.stats.criteriaCount  = criteriaCount
            searchResult.stats.categoryCount  = categoryCount
            searchResult.stats.geoObjectCount = geoObjectCount
        }

        function categoryIds(searchResult) {
            var categoryIds = []
            angular.forEach(searchResult, function(criteriaResult) {
                angular.forEach(criteriaResult.categories, function(categoryResult) {
                    if (categoryResult.include) {
                        categoryIds.push(categoryResult.category.id)
                    }
                })
            })
            return categoryIds
        }
    })
    .service("famportalService", function($http) {
        this.getFamportalCategories = function(callback) {
            var FAMPORTAL_ROOT = "famportal.root"
            $http.get("/core/topic/by_value/uri/" + FAMPORTAL_ROOT).success(function(data) {
                console.log("root", data)
                callback(data)
            })
        }

        this.getGeoObjectsByCategory = function(famportalCatId, callback) {
            $http.get("/site/category/" + famportalCatId + "/objects").success(function(data) {
                console.log("geoobjects for category", famportalCatId, data)
                callback(data)
            })
        }

        this.searchGeoObjects = function(searchTerm, callback) {
            $http.get("/site/geoobject?search=" + searchTerm).success(function(data) {
                console.log("search geoobjects", searchTerm, data)
                callback(data)
            })
        }

        this.createAssignments = function(famportalCatId, kiezatlasCatIds, callback) {
            console.log("assign Famportal category", famportalCatId, "to Kiezatlas categories", kiezatlasCatIds)
            $http.put("/famportal/category/" + famportalCatId + "?" + queryString("ka_cat", kiezatlasCatIds))
                .success(callback)
        }

        this.deleteAssignments = function(famportalCatId, geoObjectIds, callback) {
            console.log("remove Famportal category", famportalCatId, "from geo objects", geoObjectIds)
            $http.delete("/famportal/category/" + famportalCatId + "?" + queryString("geo_object", geoObjectIds))
                .success(callback)
        }

        // ---

        function queryString(paramName, values) {
            var params = []
            angular.forEach(values, function(value) {
                params.push(paramName + "=" + value)
            })
            return params.join("&")
        }
    })
    .controller("categoriesController", function($scope, $http) {
        $scope.search = function() {
            console.log("search", $scope.searchTerm)
            $http.get("/core/topic?search=*" + $scope.searchTerm + "*&field=ka2.criteria.angebot")
                .success(function(categories) {
                    $scope.categories = categories
                })
        }
    })
