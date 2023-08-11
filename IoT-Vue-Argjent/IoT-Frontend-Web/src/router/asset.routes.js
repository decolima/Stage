import { Layout, Asset, Typeasset } from '@/views/asset';

export default {
    path: '/asset',
    component: Layout,
    children: [
        { path: '', component: Asset },
        { path: '/typeasset', component: Typeasset}
    ]
};