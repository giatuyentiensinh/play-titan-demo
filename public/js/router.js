'use strict';
app.config(function($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise("/layout/home");

	$stateProvider
		.state('app', {
			abstract: true,	
			url: '/layout',
			templateUrl: 'tpl/layout.html'
		})
		.state('app.home', {
			url: '/home',
			templateUrl: 'tpl/home.html'
		})
		.state('app.profile', {
			url: '/profile',
			templateUrl: 'tpl/profile.html',
			controller: 'profileCtrl'
		})
		.state('app.graph', {
			url: '/graph',
			templateUrl: 'tpl/graph.html',
			controller: 'graphCtrl'
		});
});