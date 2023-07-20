import { Layout, List } from '@/views/qrcode-admin';

export default {
    path: '/scan',
    component: Layout,
    children: [
        { path: '', component: List },

    ]
};