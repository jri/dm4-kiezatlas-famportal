angular.module("famportal").controller("editorialController", function($scope, famportalService) {

    famportalService.getFamportalCategories(function(categories) {
        console.log("Famportal tree", categories)
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
        var searchTerm = $scope.searchTerm
        famportalService.searchGeoObjects(searchTerm, function(searchResult) {
            console.log("Geo objects with", searchTerm, searchResult)
            $scope.searchResult = searchResult.items
            initSearchResult($scope.searchResult)
            updateStats($scope.searchResult)
        })
    }

    $scope.createAssignments = function() {
        var famportalCatId = $scope.famportalCategory.id
        var kiezatlasCatIds = categoryIds($scope.searchResult)
        console.log("Assigning Famportal category", famportalCatId, "to Kiezatlas categories", kiezatlasCatIds)
        famportalService.createAssignments(famportalCatId, kiezatlasCatIds, updateGeoObjects)
    }

    $scope.deleteAssignments = function() {
        var famportalCatId = $scope.famportalCategory.id
        var geoObjectIds = selectedIds($scope.geoObjects)
        console.log("Removing Famportal category", famportalCatId, "from geo objects", geoObjectIds)
        famportalService.deleteAssignments(famportalCatId, geoObjectIds, updateGeoObjects)
    }

    // ---

    function updateGeoObjects() {
        famportalService.getGeoObjectsByCategory($scope.famportalCategory.id, function(geoObjects) {
            console.log("Geo objects for Famportal category", $scope.famportalCategory.id, geoObjects)
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