'use strict';
app.controller('graphCtrl', function($scope, $http) {
    var nodes = new vis.DataSet([]);
    var edges = new vis.DataSet([]);

    // create a network
    var container = document.getElementById('mynetwork');
    var data = {
        nodes: nodes,
        edges: edges
    };
    var options = {
        nodes: {
            borderWidth: 2,
            // shape: 'dot'
        },
        physics: true,
        interaction: {
            hover: true
        }
    };

    // var options = {
    //     nodes: {
    //         shape: 'dot',
    //         size: 30,
    //         font: {
    //             size: 12,
    //             color: 'red'
    //         },
    //         borderWidth: 2
    //     },
    //     edges: {
    //         width: 2
    //     }
    // };
    var network = new vis.Network(container, data, options);


    $http.get('/friends')
        .success(function(data) {
            console.log(data);
            edges.add(data.ids);
        })
        .error(function(data) {
            console.log(data);
        });

    $http.get('/persons')
        .success(function(data) {
            console.log(data);
            data.vexters.forEach(function(obj) {
                // obj.label = JSON.stringify(obj);
                obj.color = {
                    background: 'pink',
                    border: 'green'
                };
                // obj.color = {background:'#F03967', border:'#713E7F',highlight:{background:'red',border:'black'}};
            });

            nodes.add(data.vexters);
        })
        .error(function(data) {
            console.log(data);
        });
});
