import { Layout, List, ListPub } from '@/views/programmazione';

export default {
    path: '/programmazione',
    component: Layout,
    children: [
        { path: '', component: List },
        { path: 'pub', component: ListPub }
    ]
};


 