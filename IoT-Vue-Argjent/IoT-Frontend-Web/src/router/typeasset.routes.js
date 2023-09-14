import { Layout, Typeasset } from '@/views/typeasset';
export default {
    path: '/typeasset',
    component: Layout,
    children: [
        { path: '', component: Typeasset }
    ]
};