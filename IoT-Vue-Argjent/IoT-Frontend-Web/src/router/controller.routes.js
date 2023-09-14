import { Layout, Controller } from '@/views/controller';

export default {
    path: '/controller',
    component: Layout,
    children: [
        { path: '', component: Controller }
    ]
};