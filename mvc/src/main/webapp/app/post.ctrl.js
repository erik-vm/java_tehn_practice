(function () {
    'use strict';

    angular.module('app').controller('PostCtrl', Ctrl);

    function Ctrl(http, messageService) {

        const vm = this;
        vm.insertNew = insertNew;
        vm.deletePost = deletePost;

        vm.newPost = {};
        vm.posts = [];
        vm.errors = [];

        init();

        function init() {
            http.get('api/posts').then(function (data) {
                vm.posts = data;
                vm.errors = [];
            });
        }

        function insertNew() {
            http.post('api/posts', vm.newPost).then(function () {
                vm.newPost = {};
                init();
            }, errorHandler);
        }

        function deletePost(postId) {
            http.delete('api/posts/' + postId).then(function () {
                init();
            }, errorHandler);
        }

        function errorHandler(response) {
            if (response.data.errors) {
                vm.errors = response.data.errors.map(function (error) {

                    console.log(error.code);

                    return messageService.getMessage(error.code, error.arguments);
                });
            } else {
                console.log('Error: ' + JSON.stringify(response.data));
            }
        }
    }

})();
