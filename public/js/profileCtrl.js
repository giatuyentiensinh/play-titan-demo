'use strict';
app.controller('profileCtrl', function($scope, $http) {

    $scope.persons = [];
    $scope.vexters = [];
    $scope.currentname = "";

    $http.get('/persons')
        .success(function(data) {
            console.log(data);
            $scope.persons = data.persons;
            $scope.vexters = data.vexters;
        })
        .error(function(data) {
            console.log(data);
        });

    $scope.submit = function() {
        $http.post('/post', {
                name: $scope.name,
                age: $scope.age,
                desc: $scope.desc
            })
            .success(function(data) {
                Materialize.toast(JSON.stringify(data), 5000);
                console.log(data);
                $scope.persons.push(data);
            })
            .error(function(data) {
                Materialize.toast(JSON.stringify(data), 10000, 'rounded');
                console.log(data);
            });
    };

    $scope.removePerson = function(index) {
        $http.delete('/post/' + $scope.vexters[index].id)
            .success(function(data) {
                Materialize.toast(JSON.stringify(data), 5000);
                $scope.persons.splice(index, 1);
                console.log(data);
            })
            .error(function(data) {
                Materialize.toast(JSON.stringify(data), 10000, 'rounded');
                console.log(data);
            });
    };

    $scope.currentPerson = function(name) {
        console.log(name);
        $scope.currentname = name;
    };

    $scope.updateRelation = function() {
        $http.post('/relation', {
            person: $scope.currentname,
            inperson: $scope.inperson,
            inproperty: $scope.inproperty,
            outperson: $scope.outperson,
            outproperty: $scope.outproperty
        }).success(function(data) {
            console.log(data);
            Materialize.toast(JSON.stringify(data), 10000, 'rounded');
            $scope.inperson = $scope.outperson = $scope.outproperty = $scope.inproperty = '';
        }).error(function(data) {
            Materialize.toast(JSON.stringify(data), 10000, 'rounded');
            console.log(data);
        });
    };

    $scope.addRelationOne = function() {
        $http.post('/relation/inbind', {
            person: $scope.currentname,
            inperson: $scope.inperson,
            inproperty: $scope.inproperty,
        }).success(function(data) {
            console.log(data);
            Materialize.toast(JSON.stringify(data), 10000, 'rounded');
            $scope.inperson = $scope.outperson = $scope.outproperty = $scope.inproperty = '';
        }).error(function(data) {
            Materialize.toast(JSON.stringify(data), 10000, 'rounded');
            console.log(data);
        });
    };
});
