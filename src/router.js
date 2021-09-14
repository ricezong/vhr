import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/Login.vue'
import Home from "./views/Home";
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login,
      hidden: true
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      hidden: true,
      meta: {
        roles: ['admin', 'user']
      }
    },
    {
      path: '*',
      redirect: '/home'
    }
  ]
})
