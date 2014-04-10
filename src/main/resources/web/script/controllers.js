angular.module("famportal").controller("editorialController", function($scope, famportalService) {

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
.controller("categoriesController", function($scope, $http) {
    $scope.search = function() {
        console.log("search", $scope.searchTerm)
        $http.get("/core/topic?search=*" + $scope.searchTerm + "*&field=ka2.criteria.angebot")
            .success(function(categories) {
                $scope.categories = categories
            })
    }
})
