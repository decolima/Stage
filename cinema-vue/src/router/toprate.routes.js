import { Layout, List } from '@/views/toprate';

export default {
    path: '/toprate',
    component: Layout,
    children: [
        { path: '', component: List },

    ]
};