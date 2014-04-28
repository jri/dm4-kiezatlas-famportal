angular.module("famportal").service("famportalService", function($http) {

    this.getFamportalTree = function(callback) {
        var FAMPORTAL_ROOT = "famportal.root"
        $http.get("/core/topic/by_value/uri/" + FAMPORTAL_ROOT).success(callback)
    }

    this.getGeoObjectsByCategory = function(famportalCatId, callback) {
        $http.get("/site/category/" + famportalCatId + "/objects").success(callback)
    }

    this.searchGeoObjects = function() {
        var req = new ClockedRequest()
        return function(searchTerm, callback) {
            req.perform("GET", "/site/geoobject", {search: searchTerm}, callback)
        }
    }()

    this.createAssignments = function(famportalCatId, kiezatlasCatIds, callback) {
        $http.put("/famportal/category/" + famportalCatId + "?" + queryString("ka_cat", kiezatlasCatIds))
            .success(callback)
    }

    this.deleteAssignments = function(famportalCatId, geoObjectIds, callback) {
        $http.delete("/famportal/category/" + famportalCatId + "?" + queryString("geo_object", geoObjectIds))
            .success(callback)
    }

    this.countAssignments = function(callback) {
        $http.get("/famportal/count").success(callback)
    }

    // ---

    function ClockedRequest() {

        var clock = 0

        this.perform = function(method, url, params, callback) {
            clock++
            params.clock = clock
            $http({method: method, url: url, params: params}).success(function(data) {
                if (data.clock == clock) {
                    callback(data)
                } else {
                    console.log("Obsolete response for", method, url, params,
                        "(client clock=" + clock + ", response clock=" + data.clock + ")")
                }
            })
        }
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
