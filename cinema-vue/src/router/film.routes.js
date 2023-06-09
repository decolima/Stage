import { Layout, List, AddEdit, ListProgrammazione, AddProgrammazione, buy, tkts } from '@/views/films';

export default {
    path: '/films',
    component: Layout,
    children: [
        { path: '', component: List },
        { path: 'add', component: AddEdit },
        { path: 'edit/:id', component: AddEdit },
        { path: 'programmazione/:id', component: ListProgrammazione },
        { path: 'programmazione/add/:id', component: AddProgrammazione },
        { path: 'biglietti/:id', component: buy },
        { path: 'tkts', component: tkts }
    ]
};