import { Layout, List, AddEdit } from '@/views/sale';

export default {
    path: '/sale',
    component: Layout,
    children: [
        { path: '', component: List },
        { path: 'add', component: AddEdit },
        { path: 'edit/:id', component: AddEdit }
    ]
};