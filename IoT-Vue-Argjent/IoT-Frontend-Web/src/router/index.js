import { createRouter, createWebHistory } from 'vue-router';

import { useAuthStore, useAlertStore } from '@/stores';
import accountRoutes from './account.routes';
import tagRoutes from  './tag.routes';
import assetRoutes from './asset.routes';
import typeAssetRoutes from './typeasset.routes';
import controllerRoutes from './controller.routes';
import companyRoutes from './company.routes';
import assetMonitoringRoutes from './assetmonitoring.routes';
export const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    linkActiveClass: 'active',
    routes: [
        { ...accountRoutes },      
        { ...tagRoutes},
        { ...typeAssetRoutes},
        { ...assetRoutes},
        { ...controllerRoutes},
        { ...companyRoutes},
        { ...assetMonitoringRoutes},
        // catch all redirect to home page
        { path: '/:pathMatch(.*)*', redirect: '/company' }
    ]
});



router.beforeEach(async (to) => {
    // clear alert on route change
    const alertStore = useAlertStore();
    alertStore.clear();

    // redirect to login page if not logged in and trying to access a restricted page 
    const publicPages = ['/account/login', '/account/registration'];
    const authRequired = !publicPages.includes(to.path);
    const authStore = useAuthStore();

    if (authRequired && !authStore.isLogged) {
        authStore.returnUrl = to.fullPath;
        return '/account/login';
    }
});