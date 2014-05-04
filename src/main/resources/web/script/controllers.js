angular.module("famportal").controller("editorialController", function($scope, famportalService) {

    $scope.config = {
        MIN_SEARCH_TERM_LENGTH: 2
    }

    $scope.searchTerm = ""  // Focusing the search field via tab key triggers a search.
                            // At this time searchTerm must be initialized

    famportalService.getFamportalTree(function(famportalTree) {
        console.log("Famportal tree", famportalTree)
        $scope.famportalTree = famportalTree
        // ### TODO: use q style instead of nested callbacks
        famportalService.countAssignments(function(geoObjectCount) {
            console.log("Geo object count", geoObjectCount)
            $scope.famportalTree.count = geoObjectCount
        })
    })

    $scope.selectFamportalCategory = function(category) {
        $scope.famportalCategory = category
        updateAssignedObjects()
    }

    $scope.selectAssignedObject = function() {
        $scope.assignedObjects.selectedCount = selectedIds($scope.assignedObjects).length
    }

    $scope.selectGeoObject = function() {
        $scope.geoObjects.selectedCount = selectedIds($scope.geoObjects).length
    }

    $scope.selectKiezatlasCategory = function() {
        updateSelectedCount($scope.searchResult)
    }

    $scope.searchGeoObjects = function() {
        var searchTerm = $scope.searchTerm
        if (searchTerm.length >= $scope.config.MIN_SEARCH_TERM_LENGTH) {
            famportalService.searchGeoObjects(searchTerm, function(geoObjects) {
                console.log("Geo objects (by name) with", searchTerm, geoObjects)
                $scope.geoObjects = geoObjects.items
                $scope.geoObjects.selectedCount = geoObjects.items.length
                initGeoObjects($scope.geoObjects)
            })
            famportalService.searchCategories(searchTerm, function(searchResult) {
                console.log("Geo objects (by category) with", searchTerm, searchResult)
                $scope.searchResult = searchResult.items
                initSearchResult($scope.searchResult)
                updateSelectedCount($scope.searchResult)
            })
        } else {
            $scope.geoObjects = null
            $scope.searchResult = null
        }
    }

    $scope.createAssignments = function() {
        var famportalCatId = $scope.famportalCategory.id
        var geoObjectIds = selectedIds($scope.geoObjects)
        console.log("Assigning Famportal category", famportalCatId, "to geo objects", geoObjectIds)
        famportalService.createAssignments(famportalCatId, geoObjectIds, updateAssignedObjects)
    }

    $scope.createAssignmentsByCategories = function() {
        var famportalCatId = $scope.famportalCategory.id
        var kiezatlasCatIds = categoryIds($scope.searchResult)
        console.log("Assigning Famportal category", famportalCatId, "to Kiezatlas categories", kiezatlasCatIds)
        famportalService.createAssignmentsByCategories(famportalCatId, kiezatlasCatIds, updateAssignedObjects)
    }

    $scope.deleteAssignments = function() {
        var famportalCatId = $scope.famportalCategory.id
        var geoObjectIds = selectedIds($scope.assignedObjects)
        console.log("Removing Famportal category", famportalCatId, "from geo objects", geoObjectIds)
        famportalService.deleteAssignments(famportalCatId, geoObjectIds, updateAssignedObjects)
    }

    $scope.deleteAssignmentsByCategories = function() {
        var famportalCatId = $scope.famportalCategory.id
        var kiezatlasCatIds = categoryIds($scope.searchResult)
        console.log("Removing Famportal category", famportalCatId, "from Kiezatlas categories", kiezatlasCatIds)
        famportalService.deleteAssignmentsByCategories(famportalCatId, kiezatlasCatIds, updateAssignedObjects)
    }

    // ---

    function updateAssignedObjects() {
        var famportalCatId = $scope.famportalCategory.id
        famportalService.getGeoObjectsByCategory(famportalCatId, function(geoObjects) {
            console.log("Geo objects for Famportal category", famportalCatId, geoObjects)
            $scope.assignedObjects = geoObjects
            $scope.assignedObjects.selectedCount = 0
            $scope.famportalTree.count["cat-" + famportalCatId] = geoObjects.length
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

    function initGeoObjects(geoObjects) {
        angular.forEach(geoObjects, function(geoObject) {
            geoObject.selected = true
        })
    }

    function initSearchResult(searchResult) {
        var categoryCount = 0
        angular.forEach(searchResult, function(criteriaResult) {
            categoryCount += criteriaResult.categories.length
            angular.forEach(criteriaResult.categories, function(categoryResult) {
                if (categoryResult.geo_objects.length) {
                    categoryResult.selected = true
                }
            })
        })
        //
        searchResult.categoryCount = categoryCount
    }

    function updateSelectedCount(searchResult) {
        var selectedCount = 0
        angular.forEach(searchResult, function(criteriaResult) {
            angular.forEach(criteriaResult.categories, function(categoryResult) {
                if (categoryResult.selected) {
                    selectedCount += categoryResult.geo_objects.length
                }
            })
        })
        searchResult.selectedCount = selectedCount
    }

    function categoryIds(searchResult) {
        var categoryIds = []
        angular.forEach(searchResult, function(criteriaResult) {
            angular.forEach(criteriaResult.categories, function(categoryResult) {
                if (categoryResult.selected) {
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
