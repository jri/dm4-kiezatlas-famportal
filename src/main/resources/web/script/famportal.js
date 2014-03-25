// Application model
//   root               - Famportal category tree (topic of type "famportal.category" with all child topics)
//   famportalCategory  - Selected Famportal category (topic of type "famportal.category")
//   geoObjects         - Geo Objects assigned to selected Famportal category (array of topics + "selected" property)
//   searchResult       - Found Geo Objects, grouped by KA category (array of array of array of Geo Object topics)
//
angular.module("famportal", ["ngRoute"])
    .config(function($routeProvider) {
        $routeProvider
            .when("/editorial", {
                templateUrl: "/de.kiezatlas.famportal/partials/editorial.html",  controller: "editorialController"
            })
            .when("/categories", {
                templateUrl: "/de.kiezatlas.famportal/partials/categories.html", controller: "categoriesController"
            })
            .otherwise({redirectTo: "/editorial"})
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

        $scope.searchGeoObjects = function() {
            famportalService.searchGeoObjects($scope.searchTerm, function(searchResult) {
                $scope.searchResult = searchResult.search_result
            })
        }

        $scope.assignToFamportalCategory = function() {
            famportalService.assignToFamportalCategory($scope.famportalCategory.id, geoObjectIds($scope.searchResult),
                updateGeoObjects)
        }

        $scope.removeFromFamportalCategory = function() {
            famportalService.removeFromFamportalCategory($scope.famportalCategory.id, selectedIds($scope.geoObjects),
                updateGeoObjects)
        }

        // ---

        function updateGeoObjects() {
            famportalService.getGeoObjectsByCategory($scope.famportalCategory.id, function(geoObjects) {
                $scope.geoObjects = geoObjects
                geoObjects.selectedCount = 0
            })
        }

        // ---

        function selectedIds(geoObjects) {
            var selected = []
            angular.forEach(geoObjects, function(geoObject) {
                if (geoObject.selected) {
                    selected.push(geoObject.id)
                }
            })
            return selected
        }

        function geoObjectIds(searchResult) {
            var geoObjectIds = []
            angular.forEach(searchResult, function(criteriaResult) {
                angular.forEach(criteriaResult.categories, function(categoryResult) {
                    angular.forEach(categoryResult.geo_objects, function(geoObject) {
                        geoObjectIds.push(geoObject.id)
                    })
                })
            })
            return geoObjectIds
        }
    })
    .service("famportalService", function($http) {
        this.getFamportalCategories = function(callback) {
            var FAMPORTAL_CATEGORY_ROOT = "famportal.category.root"
            $http.get("/core/topic/by_value/uri/" + FAMPORTAL_CATEGORY_ROOT).success(function(data) {
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

        this.assignToFamportalCategory = function(famportalCatId, geoObjectIds, callback) {
            console.log("assign famportal category", famportalCatId, "to geo objects", geoObjectIds)
            $http.put("/famportal/category/" + famportalCatId + "?" + queryString("geo_object_id", geoObjectIds))
                .success(callback)
        }

        this.removeFromFamportalCategory = function(famportalCatId, geoObjectIds, callback) {
            console.log("remove famportal category", famportalCatId, "from geo objects", geoObjectIds)
            $http.delete("/famportal/category/" + famportalCatId + "?" + queryString("geo_object_id", geoObjectIds))
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
