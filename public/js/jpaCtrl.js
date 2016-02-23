'use strict';
app.controller('jpaCtrl', function($scope, $http) {

    $scope.initData = function() {
        $http.get('/users')
            .success(function(data) {
                console.log(data);
                $scope.users = data;
            })
            .error(function(data) {
                console.log(data);
            });
    };

    $scope.submit = function() {
        $http.post('/save', {
            name: $scope.username
        }).success(function(data) {
            Materialize.toast('Tạo thành công', 3000, 'rounded');
            $scope.initData();
        }).error(function(data) {
            Materialize.toast(JSON.stringify(data), 5000);
        });
    };
});
