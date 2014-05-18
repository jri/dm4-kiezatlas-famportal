angular.module("famportal").directive("multiFacet", function() {
    return {
        restrict: "E",
        transclude: true,
        template: "<span ng-transclude></span>: ",
        link: function(scope, element, attrs, controller) {
            var values = ""
            angular.forEach(scope.detailGeoObject.composite[attrs.childTypeUri], function(facet) {
                if (values) {
                    values += ", "
                }
                values += facet.value
            })
            element.append(values + "<br>")
        }
    }
})
