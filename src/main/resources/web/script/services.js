angular.module("famportal").service("famportalService", function($http) {

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
