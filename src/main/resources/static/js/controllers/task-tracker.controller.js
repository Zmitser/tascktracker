var app = angular.module('myTaskTrackerApp', ['ngRoute']);

app.controller('ProjectController', function ($scope, $http, $routeParams, $location) {
    $scope.refresh = function () {
        $http.get('/rest/developer/projects/').success(function (data) {
            $scope.projects = data;
        });
    };

    $scope.allProjects = function () {
        $scope.refresh();
    };
    $scope.removeProject = function (projectId) {
        $http.delete('/rest/manager/projects/project/delete/' + projectId).success(function (data) {
            $scope.refresh();
        }).error(function () {
            alert("Acces Denied!")
        });
    };


    $scope.getProject = function () {
        if ($routeParams.projectId != undefined && $routeParams.projectId != null) {
            $http.get('/rest/manager/projects/project/' + $routeParams.projectId).success(function (data) {
                $scope.project = data;
            }).error(function () {
                alert("Acces Denied!")
            });
        }
    };
    $scope.saveProject = function () {
        var project = {
            "name": $scope.project.name,
            "description": $scope.project.description,
            "projectId": $scope.project.projectId
        };

        $http({
            'url': '/rest/manager/projects/project/save',
            'method': 'POST',
            'headers': {'Content-Type': 'application/json'},
            'data': project
        }).success(function () {
            $location.path("/projects");
        }).error(function () {
            alert("Acces Denied!")
        })
    };

});


app.controller('TaskController', function ($scope, $http, $routeParams, $location) {
    $scope.refresh = function () {
        $http.get('/rest/developer/tasks/' + $routeParams.projectId).success(function (data) {
            $scope.tasks = data;
        });
    };

    $scope.allTasks = function () {
        $scope.refresh();
    };

    $scope.deleteTask = function (taskId) {
        $http.delete('/rest/manager/tasks/task/delete/' + taskId).success(function () {
            $scope.refresh();
        });
    };
    $scope.getTask = function () {
        if ($routeParams.taskId != undefined && $routeParams.taskId != null) {
            $http.get('/rest/manager/tasks/task/' + $routeParams.taskId).success(function (data) {
                $scope.task = data;
            });
        }
    };

    $scope.saveTask = function () {
        var task = {
            "name": $scope.task.name,
            "description": $scope.task.description,
            "isCompleted": $scope.task.isCompleted,
            "taskId": $scope.task.taskId,
            "user":{
                "userId": $scope.task.user.userId
            },
            "project":{
                "projectId": $scope.task.project.projectId
            }
        };

        $http({
            'url': '/rest/manager/tasks/task/save',
            'method': 'POST',
            'headers': {'Content-Type': 'application/json'},
            'data': task
        }).success(function () {
            $location.path("/projects");
        }).error(function () {
            alert("Acces Denied!")
        })
    };

    $scope.changeStatus = function (taskId, isCompleted) {
        var task = {
            "isCompleted": ""+isCompleted,
            "taskId": ""+taskId
        };
        $http({
            'url': '/rest/developer/tasks/task/save',
            'method': 'POST',
            'headers': {'Content-Type': 'application/json'},
            'data': task
        }).success(function () {
            $location.path("/projects");
        })
    }


});


app.controller('ManagerController', function ($scope, $http, $location, $routeParams) {
    $scope.refresh = function () {
        $http.get('/rest/manager/users/').success(function (data) {
            $scope.users = data;
        });
    };

    $scope.allUsers = function () {
        $scope.refresh();
    };
    $scope.removeUser = function (userId) {
        $http.delete('/rest/manager/users/user/delete/' + userId).success(function () {
            $scope.refresh();
        }).error(function () {
            alert("Acces Denied!")
        });
    };
    $scope.getUser = function () {
        if ($routeParams.userId != undefined && $routeParams.userId != null && $routeParams.userId) {
            $http.get('/rest/manager/users/user/' + $routeParams.userId).success(function (data) {
                $scope.user = data;
            }).error(function () {
                alert("Acces Denied!")
            });
        }
    };
    $scope.saveUser = function () {
        var user = {
            "username": $scope.user.username,
            "password": $scope.user.password,
            "email": $scope.user.email,
            "userId": $scope.user.userId
        };

        $http({
            'url': '/rest/manager/users/user/save',
            'method': 'POST',
            'headers': {'Content-Type': 'application/json'},
            'data': user
        }).success(function () {
            $location.path("/developers");
        })
    };
});


app.controller('CommentController', function ($scope, $window, $http, $routeParams, $location) {

    $scope.refresh = function () {
        $http.get('/rest/developer/comments/' + $routeParams.taskId).success(function (data) {
            $scope.comments = data;
        });
    };

    $scope.allComments = function () {
        $scope.refresh();
    };

    $scope.deleteComment = function (commentId) {
        $http.delete('/rest/developer/comments/comment/delete/' + commentId).success(function () {
            $scope.refresh();
        });
    };
    $scope.getComment= function () {
        if ($routeParams.commentId != undefined && $routeParams.commentId != null) {
            $http.get('/rest/developer/comments/comment/' + $routeParams.commentId).success(function (data) {
                $scope.comment = data;
            });
        }
    };

    $scope.changeComment = function () {
        var comment = {
            "text": $scope.comment.text,
            "commentId": $scope.comment.commentId
        };

        $http({
            'url': '/rest/developer/comments/comment/save',
            'method': 'POST',
            'headers': {'Content-Type': 'application/json'},
            'data': comment
        }).success(function () {
            $window.history.back();
            $scope.refresh();
        })
    };


    $scope.postComment = function () {
        var comment = {
            "text": $scope.comment.text,
            "commentId": $scope.comment.commentId,
            "task":{
                "taskId": $routeParams.taskId
            }
        };

        $http({
            'url': '/rest/developer/comments/comment/save',
            'method': 'POST',
            'headers': {'Content-Type': 'application/json'},
            'data': comment
        }).success(function () {
            $scope.refresh();
            $scope.comment.text = '';
        })
    };



});

//ngRoute definition
//a route is defined after #sign #/posts/1 or #/postsJSON
app.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/projects', {
            templateUrl: '/partials/projects.html',
            controller: 'ProjectController'
        }).when('/project/tasks/:projectId', {
            templateUrl: '/partials/tasks.html',
            controller: 'TaskController'
        }).when('/project/:projectId', {
            templateUrl: '/partials/form-project.html',
            controller: 'ProjectController'
        }).when('/project', {
            templateUrl: '/partials/form-project.html',
            controller: 'ProjectController'
        }).when('/task/:taskId', {
            templateUrl: '/partials/form-task.html',
            controller: 'TaskController'
        }).when('/task', {
            templateUrl: '/partials/form-task.html',
            controller: 'TaskController'
        }).when('/developers', {
            templateUrl: '/partials/developers.html',
            controller: 'ManagerController'
        }).when('/developer', {
            templateUrl: '/partials/form-developer.html',
            controller: 'ManagerController'
        }).when('/developer/:userId', {
            templateUrl: '/partials/form-developer.html',
            controller: 'ManagerController'
        }).when('/task/discussion/:taskId', {
            templateUrl: '/partials/task-discussion.html',
            controller: 'CommentController'
        }).when('/comment/:commentId', {
            templateUrl: '/partials/form-comment.html',
            controller: 'CommentController'
        }).otherwise({
            redirectTo: '/projects'
        });
    }]);

