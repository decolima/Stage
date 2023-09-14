import { Layout, Asset } from '@/views/asset';
export default {
    path: '/asset',
    component: Layout,
    children: [
        { path: '', component: Asset }
    ]
};