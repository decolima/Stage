import { Layout, AssetMonitoring } from '@/views/assetmonitoring';

export default {
    path: '/monitoring',
    component: Layout,
    children: [
        { path: '', component: AssetMonitoring }
    ]
};