import { Layout, Company } from '@/views/company';

export default {
    path: '/company',
    component: Layout,
    children: [
        { path: '', component: Company }
    ]
};