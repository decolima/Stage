import { Layout, Tag } from '@/views/tag';

export default {
    path: '/tag',
    component: Layout,
    children: [
        { path: '', component: Tag }
    ]
};