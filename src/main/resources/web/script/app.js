angular.module("famportal", [])
    .controller("famportalController", function($scope, $http, famportalService) {

        var FAMPORTAL_CATEGORY_ROOT = "famportal.category.root"

        $http.get("/core/topic/by_value/uri/" + FAMPORTAL_CATEGORY_ROOT).success(function(data) {
            console.log("root", data)
            $scope.root = data
        })

        $scope.search = function() {
            console.log("search", $scope.searchTerm)
            $http.get("/core/topic?search=*" + $scope.searchTerm + "*&field=ka2.criteria.angebot").success(function(data) {
                $scope.categories = data
            })
        }

        $scope.showGeoObjects = function(famportalCategoryId) {
            famportalService.getGeoObjectsByCategory(famportalCategoryId, function(geoObjects) {
                $scope.geoObjects = geoObjects
            })
        }
    })
    .service("famportalService", function($http) {
        this.getGeoObjectsByCategory = function(famportalCategoryId, callback) {
            $http.get("/famportal/geoobject/category/" + famportalCategoryId).success(function(data) {
                console.log("geoobjects for category", famportalCategoryId, data)
                callback(data.items)
            })
        }
    })
