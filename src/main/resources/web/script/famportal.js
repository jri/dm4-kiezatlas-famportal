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
            famportalService.getGeoObjectsByCategory(category.id, function(geoObjects) {
                $scope.geoObjects = geoObjects
            })
        }

        $scope.assignToFamportalCategory = function() {
            famportalService.assignToFamportalCategory($scope.famportalCategory.id, geoObjectIds($scope.searchResult))

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
        }

        $scope.searchGeoObjects = function() {
            famportalService.searchGeoObjects($scope.searchTerm, function(searchResult) {
                $scope.searchResult = searchResult.search_result
            })
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

        this.getGeoObjectsByCategory = function(famportalCategoryId, callback) {
            $http.get("/site/category/" + famportalCategoryId + "/objects").success(function(data) {
                console.log("geoobjects for category", famportalCategoryId, data)
                callback(data)
            })
        }

        this.assignToFamportalCategory = function(famportalCategoryId, geoObjectIds) {
            var FAMPORTAL_CATEGORY_FACET = "famportal.category.facet"
            console.log("assign famportal category", famportalCategoryId, "to geo objects", geoObjectIds)
            $http.put("/famportal/category_facet/" + FAMPORTAL_CATEGORY_FACET + "/category/" + famportalCategoryId +
                "?" + queryString("geo_object_id", geoObjectIds)).success(function() {
                // ### TODO
            })

            function queryString(paramName, values) {
                var params = []
                angular.forEach(values, function(value) {
                    params.push(paramName + "=" + value)
                })
                return params.join("&")
            }
        }

        this.searchGeoObjects = function(searchTerm, callback) {
            $http.get("/site/geoobject?search=" + searchTerm).success(function(data) {
                console.log("search geoobjects", searchTerm, data)
                callback(data)
            })
        }
    })
    .controller("categoriesController", function($scope, famportalService) {
        $scope.search = function() {
            console.log("search", $scope.searchTerm)
            $http.get("/core/topic?search=*" + $scope.searchTerm + "*&field=ka2.criteria.angebot")
                .success(function(categories) {
                    $scope.categories = categories
                })
        }
    })
