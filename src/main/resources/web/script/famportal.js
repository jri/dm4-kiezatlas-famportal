angular.module("famportal", ["ngRoute"])
    .config(function($routeProvider) {
        $routeProvider
            .when("/editorial", {
                templateUrl: "/de.kiezatlas.famportal/partials/editorial.html",  controller: "famportalController"
            })
            .when("/categories", {
                templateUrl: "/de.kiezatlas.famportal/partials/categories.html", controller: "searchController"
            })
            .otherwise({redirectTo: "/editorial"})
    })
    .controller("famportalController", function($scope, famportalService) {

        famportalService.getFamportalCategories(function(categories) {
            $scope.root = categories
        })

        $scope.showGeoObjects = function(famportalCategoryId) {
            famportalService.getGeoObjectsByCategory(famportalCategoryId, function(geoObjects) {
                $scope.geoObjects = geoObjects
            })
        }

        $scope.searchGeoObjects = function() {
            famportalService.searchGeoObjects($scope.searchTerm, function(searchResult) {
                $scope.searchResult = searchResult.search_result
            })
        }
    })
    .controller("searchController", function($scope, famportalService) {
        $scope.search = function() {
            console.log("search", $scope.searchTerm)
            famportalService.searchAngebotsArten($scope.searchTerm, function(categories) {
                $scope.categories = categories
            })
        }
    })
    .service("famportalService", function($http) {
        var FAMPORTAL_CATEGORY_ROOT = "famportal.category.root"

        this.getFamportalCategories = function(callback) {
            $http.get("/core/topic/by_value/uri/" + FAMPORTAL_CATEGORY_ROOT).success(function(data) {
                console.log("root", data)
                callback(data)
            })
        }

        this.getGeoObjectsByCategory = function(famportalCategoryId, callback) {
            $http.get("/site/category/" + famportalCategoryId + "/objects").success(function(data) {
                console.log("geoobjects for category", famportalCategoryId, data)
                callback(data)
            })
        }

        this.searchGeoObjects = function(searchTerm, callback) {
            $http.get("/site/geoobject?search=" + searchTerm).success(function(data) {
                console.log("search geoobjects", searchTerm, data)
                callback(data)
            })
        }

        this.searchAngebotsArten = function(searchTerm, callback) {
            $http.get("/core/topic?search=*" + searchTerm + "*&field=ka2.criteria.angebot").success(function(data) {
                callback(data)
            })
        }
    })
