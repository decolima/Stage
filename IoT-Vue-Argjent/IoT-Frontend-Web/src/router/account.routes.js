
import { Login } from '@/views/account';
import { Layout } from '@/views/account';

export default {
    path: '/account',
    component: Layout,
    children: [
        { path: '', redirect: 'login' },
        { path: 'login', component: Login }
    ]
};
