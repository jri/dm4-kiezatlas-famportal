angular.module("famportal").service("famportalService", function($http) {

    this.getFamportalCategories = function(callback) {
        var FAMPORTAL_ROOT = "famportal.root"
        $http.get("/core/topic/by_value/uri/" + FAMPORTAL_ROOT).success(callback)
    }

    this.getGeoObjectsByCategory = function(famportalCatId, callback) {
        $http.get("/site/category/" + famportalCatId + "/objects").success(callback)
    }

    this.searchGeoObjects = function(searchTerm, callback) {
        $http.get("/site/geoobject?search=" + searchTerm).success(callback)
    }

    this.createAssignments = function(famportalCatId, kiezatlasCatIds, callback) {
        $http.put("/famportal/category/" + famportalCatId + "?" + queryString("ka_cat", kiezatlasCatIds))
            .success(callback)
    }

    this.deleteAssignments = function(famportalCatId, geoObjectIds, callback) {
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
