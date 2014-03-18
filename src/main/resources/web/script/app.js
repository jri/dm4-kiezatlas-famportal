function SearchController($scope, $http) {

    $scope.search = function() {
        console.log("search", $scope.searchTerm)
        $http.get("/core/topic?search=*" + $scope.searchTerm + "*&field=ka2.criteria.angebot").success(function(data) {
            $scope.categories = data
        })
    }
}
